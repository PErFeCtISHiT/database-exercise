package project;

import project.context.costContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        //为每个用户生成一个月账单（每25000个用户分别订购了1，2，3，4，5套餐
        generateUserBill();
    }

    private static void generateUserBill() {
        DecimalFormat df = new DecimalFormat("0.00");//保留两位
        File file = new File("user_bill.sql");
        StringBuilder str;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
            str = new StringBuilder("lock tables `bill` write;\r\n");
            bufferedWriter.write(str.toString());
            str = new StringBuilder("insert into `bill`(id,bill_date," +
                    "call_month,internal_flow_month,local_flow_month," +
                    "message_month,call_cost,internal_flow_cost,local_flow_cost" +
                    ",message_cost,user_id) values ");
            for (int i = 0; i < 100000; i++) {
                int callMonth = (int) (Math.random() * (costContext.CALL_NUM * 2));
                double internalFlowMonth = Math.random() * (costContext.INTERNAL_FLOW_NUM * 2);
                double localFlowMonth = Math.random() * (costContext.LOCAL_FLOW_NUM * 2);
                int messageMonth = (int) (Math.random() * (costContext.MESSAGE_NUM * 2));
                double callCost, messageCost, internalFlowCost, localFlowCost;
                str.append("(").append(i + 1).append(",'").append(date.toString()).append("',")
                        .append(callMonth).append(",").append(df.format(internalFlowMonth))
                        .append(",").append(df.format(localFlowMonth)).append(",").append(messageMonth);
                //当月花费减去套餐额度
                if (i < 20000) {
                    callMonth -= costContext.CALL_NUM;
                    if(callMonth < 0)//套餐未使用完
                        callMonth = 0;
                }
                else if( i < 40000){
                    messageMonth -= costContext.MESSAGE_NUM;
                    if(messageMonth < 0)
                        messageMonth = 0;
                }
                else if(i < 60000){
                    localFlowMonth -= costContext.LOCAL_FLOW_NUM;
                    if(localFlowMonth < 0)
                        localFlowMonth = 0;
                }
                else if(i < 80000){
                    internalFlowMonth -= costContext.INTERNAL_FLOW_NUM;
                    if(internalFlowMonth < 0)
                        internalFlowMonth = 0;
                }
                else {
                    callMonth -= costContext.CALL_NUM / 2;
                    if(callMonth < 0)
                        callMonth = 0;
                    messageMonth -= costContext.MESSAGE_NUM / 2;
                    if(messageMonth < 0)
                        messageMonth = 0;
                    localFlowMonth -= costContext.LOCAL_FLOW_NUM / 2;
                    if(localFlowMonth < 0)
                        localFlowMonth = 0;
                    internalFlowMonth -= costContext.INTERNAL_FLOW_NUM / 2;
                    if(internalFlowMonth < 0)
                        internalFlowMonth = 0;
                }
                callCost = callMonth * costContext.CALL_PRICE;
                messageCost = messageMonth * costContext.MESSAGE_PRICE;
                localFlowCost = localFlowMonth * costContext.LOCAL_FLOW_PRICE;
                internalFlowCost = internalFlowMonth * costContext.INTERNAL_FLOW_PRICE;
                str.append(",").append(df.format(callCost)).append(",")
                        .append(df.format(internalFlowCost)).append(",")
                        .append(df.format(localFlowCost)).append(",")
                        .append(df.format(messageCost)).append(",")
                        .append(i + 1).append("),");
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
            for (int i = 0; i < 200000; i++) {
                str.append("(").append(i + 1).append(",'").append(date.toString())
                        .append("',").append((int) (Math.random() * 5 + 1)).append(",")
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
            for (int i = 0; i < 100000; i += 2) {
                str.append("(").append(i + 1).append(",").append((int) (Math.random() * 5 + 1))
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
                str.append("(").append(i + 1).append(",").append((int) (Math.random() * (costContext.CALL_NUM * 2)))
                        .append(",").append((int) (Math.random() * (costContext.MESSAGE_NUM * 2))).append(",")
                        .append(df.format(Math.random() * (costContext.LOCAL_FLOW_NUM * 2))).append(",")
                        .append(df.format(Math.random() * (costContext.INTERNAL_FLOW_NUM * 2))).append("),");
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
