package com.pealipala.common.constant;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/22 23:22
 */
public class WareConstant {
    public enum PurchaseStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVE(2,"已领取"),
        FINISH(3,"已完成"),
        ERROR(4,"有异常");
        private int code;
        private String message;
        PurchaseStatusEnum(int code,String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
    public enum PurchaseDeatilStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVE(2,"正在采购"),
        FINISH(3,"已完成"),
        ERROR(4,"采购失败");
        private int code;
        private String message;
        PurchaseDeatilStatusEnum(int code,String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
