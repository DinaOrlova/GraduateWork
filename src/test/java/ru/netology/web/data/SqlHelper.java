package ru.netology.web.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    public static void deleteData() {
        val deleteOrder = "DELETE FROM order_entity";
        val deletePaymentEntity = "DELETE FROM payment_entity";
        val deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(url, user, password);
        ) {
            runner.update(conn, deleteOrder);
            runner.update(conn, deletePaymentEntity);
            runner.update(conn, deleteCreditRequestEntity);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getPaymentStatus() {
        String status = "";
        val statusSQL = "SELECT status FROM payment_entity;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(url, user, password);
                ) {
            status = runner.query(conn, statusSQL, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static String getCreditStatus() {
        String status = "";
        val statusSQL = "SELECT status FROM credit_request_entity;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(url, user, password);
        ) {
            status = runner.query(conn, statusSQL, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static Long getOrderCount() {
        Long count = null;
        val countSQL = "SELECT COUNT(*) FROM order_entity;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(url, user, password);
        ) {
            count = runner.query(conn, countSQL, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
}
