package project;

import java.io.*;
import java.text.DecimalFormat;

/**
 * @author: pis
 * @description: 产生项目的测试数据
 * @date: create in 16:05 2018/10/13
 */
public class testDataGenerator {
    public static void main(String[] args) {
        //生成10万给用户
        generateUser();
        //为id为奇数的用户生成一个套餐
        generateUserDiscount();
        //为每个用户生成两个历史套餐
        generateUserOrder();
        //为每个用户生成一个月账单
        generateUserBill();
    }

    private static void generateUserBill() {
        File file = new File("user_bill.sql");
        StringBuilder str;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
            str = new StringBuilder("lock tables `user_bill` write;\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("insert into `user_bill` values ");
            for(int i = 0;i < 100000;i++){
                str.append("(").append(i + 1).append(",'").append(date.toString())
                        .append("',").append((int)(Math.random() * 5 + 1)).append(",")
                        .append((i / 2) + 1).append("),");
            }
            str.deleteCharAt(str.length() - 1).append(";\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("unlock tables;\r\n");
            bufferedWriter.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateUserOrder() {
        File file = new File("user_order.sql");
        StringBuilder str;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
            str = new StringBuilder("lock tables `user_order` write;\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("insert into `user_order` values ");
            for(int i = 0;i < 200000;i++){
                str.append("(").append(i + 1).append(",'").append(date.toString())
                        .append("',").append((int)(Math.random() * 5 + 1)).append(",")
                        .append((i / 2) + 1).append("),");
            }
            str.deleteCharAt(str.length() - 1).append(";\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("unlock tables;\r\n");
            bufferedWriter.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateUserDiscount() {
        File file = new File("user_discount.sql");
        StringBuilder str;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            str = new StringBuilder("lock tables `user_discount` write;\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("insert into `user_discount` values ");
            for(int i = 0;i < 100000;i+=2){
                str.append("(").append(i + 1).append(",").append((int)(Math.random() * 5 + 1))
                        .append("),");
            }
            str.deleteCharAt(str.length() - 1).append(";\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("unlock tables;\r\n");
            bufferedWriter.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateUser() {
        DecimalFormat df = new DecimalFormat("0.00");//保留两位
        File file = new File("user.sql");
        StringBuilder str;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            str = new StringBuilder("lock tables `user` write;\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("insert into `user` values ");
            for (int i = 0; i < 100000; i++) {
                str.append("(").append(i + 1).append(",").append((int) (Math.random() * 200))
                        .append(",").append((int) (Math.random() * 400)).append(",")
                        .append(df.format(Math.random() * 4096)).append(",")
                        .append(df.format(Math.random() * 4096)).append("),");
            }
            str.deleteCharAt(str.length() - 1).append(";\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("unlock tables;\r\n");
            bufferedWriter.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
