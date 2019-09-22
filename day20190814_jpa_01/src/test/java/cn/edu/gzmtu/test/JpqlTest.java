package cn.edu.gzmtu.test;

import cn.edu.gzmtu.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class JpqlTest {

    /**
     * 查询所有
     *      jqpl：from cn.edu.gzmtu.domain.Customer
     *      sql：SELECT * FROM cst_customer
     */
    @Test
    public void test_findAll() {
        // 1.获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        // 3.执行操作：查询所有
        String jpql = "from Customer";
        // 创建Query查询对象，query对象才是执行jqpl的对象
        Query query = entityManager.createQuery(jpql);
        // 发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o.toString());
        }

        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 排序查询
     *      sql：SELECT * FROM cst_customer ORDER BY cust_id DESC
     *       jpql：from Customer order by custId desc
     *
     * 进行jpql查询
     *      1.创建query查询对象
     *      2.对参数进行赋值
     *      3.查询，并得到返回结果
     */
    @Test
    public void test_order() {
        // 1.获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        // 3.执行操作：查询所有
        String jpql = "from Customer order by custId desc"; // 根据custId排序
        // 创建Query查询对象，query对象才是执行jqpl的对象
        Query query = entityManager.createQuery(jpql);
        // 发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o.toString());
        }

        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 统计查询
     * 使用jpql查询，统计客户的总数
     *      sql：SELECT COUNT(cust_id) FROM cst_customer
     *      jpql：select count(custId) from Customer
     */
    @Test
    public void test_count() {
        // 1.获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        // 3.执行操作：查询所有
        // 3.1根据jpql语句创建Query查询对象
        String jpql = "select count(custId) from Customer";
        // 创建Query查询对象，query对象才是执行jqpl的对象
        Query query = entityManager.createQuery(jpql);
        // 3.2对参数赋值
        // 3.3发送查询，并封装结果
        /**
         * getResultList ： 直接将查询结果封装为list集合
         * getSingleResult : 得到唯一的结果集
         */
        Object count = query.getSingleResult();
        System.out.println("count = " + count);

        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     *      sql：select * from cst_customer limit 0,2
     *      jqpl : from Customer
     */
    @Test
    public void test_paged() {
        // 1.获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        // 3.执行操作：查询所有
        // 3.1根据jpql语句创建Query查询对象
        String jpql = "from Customer";
        // 创建Query查询对象，query对象才是执行jqpl的对象
        Query query = entityManager.createQuery(jpql);
        // 3.2对分页参数赋值
        // 起始索引
        query.setFirstResult(0); // 第一页
        // 每页查询的条数
        query.setMaxResults(2); // 2条数据
        // 3.3发送查询，并封装结果
        List customerList = query.getResultList();
        for (Object customer : customerList) {
            System.out.println(customer);
        }

        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     *      模糊查询：查询客户名称以‘广’开头的客户
     *          sql：SELECT * FROM cst_customer WHERE cust_name LIKE  ?
     *          jpql : from Customer where custName like ?
     */
    @Test
    public void test_condition() {
        // 1.获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        // 3.执行操作：查询所有
        // 3.1根据jpql语句创建Query查询对象
        String jpql = "from Customer where custName like ?";
        // 创建Query查询对象，query对象才是执行jqpl的对象
        Query query = entityManager.createQuery(jpql);
        // 3.2对查询参数赋值
        // setParameter(占位符索引[位置从1开始]，取值)
        query.setParameter(1, "广%"); // 设置第一个参数的值
        // 3.3发送查询，并封装结果
        List customerList = query.getResultList();
        for (Object customer : customerList) {
            System.out.println(customer);
        }

        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }
}
