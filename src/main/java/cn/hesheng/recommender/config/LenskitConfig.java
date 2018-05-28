package cn.hesheng.recommender.config;


import cn.hesheng.recommender.lenskit.Session;
import cn.hesheng.recommender.lenskit.sql.BasicServerSQLStatementFactory;
import cn.hesheng.recommender.lenskit.sql.JDBCRatingServerDAO;
import cn.hesheng.recommender.model.Ratings;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.sql.JDBCRatingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
public class LenskitConfig {


    /**
     * 初始化推荐数据源
     * @param dataSource
     * @return
     */
    @Bean
    @Autowired
    public JDBCRatingDAO jdbcRatingDAO(DataSource dataSource){
        Connection cxn = null;
        JDBCRatingDAO dao = null;
        try {
            cxn = dataSource.getConnection();
            dao = JDBCRatingDAO.newBuilder().build(cxn);
        }  catch (SQLException e) {
            log.error(e.getMessage(),e);
        }finally {
            try {
                cxn.close();
            } catch (SQLException e) {
                log.error(e.getMessage(),e);
            }
        }
        Preconditions.checkNotNull(dao,"推荐者数据源失败");
        return dao;
    }

    /**
     * recommender session
     * @param dataSource
     * @return
     */
    @Bean
    @Autowired
    public JDBCRatingServerDAO initJDBCRatingServerDAO(DataSource dataSource){
        JDBCRatingServerDAO dao = null;
        log.debug("init recommender session ");
        try {
            BasicServerSQLStatementFactory basicServerSQLStatementFactory = new BasicServerSQLStatementFactory();
            dao = new JDBCRatingServerDAO(dataSource.getConnection(),basicServerSQLStatementFactory,false);
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
        }
        Preconditions.checkNotNull(dao,"JDBCRatingServerDAO 构建失败");
        return dao;
    }

    /**
     * 初始化推荐器
     * @param serverDAO
     * @return
     */
    @Bean
    @Autowired
    public Session initLenskitRecommender(JDBCRatingServerDAO serverDAO){
        LenskitRecommender rec = null;
        try {
            LenskitConfiguration config = new LenskitConfiguration();
            config.addComponent(serverDAO);
            /* additional configuration */
            rec = LenskitRecommender.build(config);
            /* do things with the recommender */
        } catch (RecommenderBuildException e) {
            log.error(e.getMessage(),e);
        } finally {

        }
        Preconditions.checkNotNull(rec,"推荐者构建失败");
        Session session = new Session(rec,serverDAO);
        Preconditions.checkNotNull(session,"推荐者session构建失败");
        log.info("session {} ",session);
        return session;
    }
}
