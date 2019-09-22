package cn.edu.gzmtu.test;

import cn.edu.gzmtu.domain.Customer;
import cn.edu.gzmtu.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    /**
     * 保存
     *      保存客户到数据库
     *  Jpa的操作步骤
     *     1.加载配置文件创建工厂（实体管理器工厂）对象
     *     2.通过实体管理器工厂获取实体管理器
     *     3.获取事务对象，开启事务
     *     4.完成增删改查操作
     *     5.提交事务（回滚事务）
     *     6.释放资源
     */
    @Test
    public void test_save() {
        Customer customer = new Customer();
        customer.setCustName("广州航海学院");
        customer.setCustIndustry("教育");

        // 1.加载配置文件创建工厂（实体管理器工厂）对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        // 2.通过实体管理器工厂获取实体管理器
        EntityManager em = factory.createEntityManager();
        // 3.获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction();// 获取事务对象
        tx.begin(); // 开启事务
        // 4.完成增删改查操作: 保存客户到数据库
        em.persist(customer); // 保存操作
        // 5.提交事务（回滚事务）
        tx.commit();
        // 6.释放资源
        em.close();
        factory.close();
    }

    @Test
    public void test_useJpaUtils_save() {
        Customer customer = new Customer();
        customer.setCustName("广东理工学院");
        customer.setCustIndustry("教育");

        EntityManager em = JpaUtils.getEntityManager();

        // 获取事务管理器
        EntityTransaction tx = em.getTransaction();
        // 开启事务
        tx.begin();
        // 执行保存操作
        em.persist(customer);
        // 提交事务
        tx.commit();

        // 释放资源
        em.close();

    }

    /**
     * 根据id查询客户：find()
     *      (1)查询的对象就是当前客户对象本身
     *      (2)在调用find方法的时候，就会发送sql语句查询数据库(立即加载)
     */
    @Test
    public void test_find() {
        // 1.根据工具类获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        // 3.执行查询操作
        /**
         * find: 根据id查询数据
         *  参数：
         *      class：查询数据的结果需要包装成的实体类类型的字节码
         *      id：查询的主键的取值
         */
        Customer customer = entityManager.find(Customer.class, 1L);
        System.out.println("customer = " + customer);
        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 根据id查询客户：getReference()
     *      (1)获取的对象是一个动态代理对象
     *      (2)调用getReference()方法不会立即发送sql语句查询数据库
     *             * 当调用查询结果对象的时候，才会发送查询的sql语句：什么时候用，什么时候发送sql语句查询数据库
     *
     * 延迟加载（懒加载）
     *      * 得到的是一个动态代理对象
     *      * 什么时候用，什么使用才会查询
     */
    @Test
    public void test_getReference() {
        // 1.根据工具类获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        // 3.执行查询操作
        Customer customer = entityManager.getReference(Customer.class, 1L);
        System.out.println("customer = " + customer);
        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 根据id删除客户：remove()
     */
    @Test
    public void test_remove() {

        // 1.根据工具类获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        // 3.执行操作
        // 3.1 根据id查询
        Customer customer = entityManager.getReference(Customer.class, 1L);
        // 3.2 删除
        entityManager.remove(customer);
        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 更新客户：merge()
     */
    @Test
    public void test_update() {
        // 1.根据工具类获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        // 3.执行操作
        // 3.1 根据id查询操作
        Customer customer = entityManager.getReference(Customer.class, 1L);
        customer.setCustName("广州航海学院"); // 修改
        customer.setCustAddress("黄浦区");
        // 3.2 更新数据库
        entityManager.merge(customer);
        // 4.提交事务
        tx.commit();
        // 5.释放资源
        entityManager.close();
    }
}
