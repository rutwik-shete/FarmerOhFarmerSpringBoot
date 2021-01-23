package com.rutwik.farmerohfarmer;

public class Constants {
    
    public final static String SCHEMA_NAME = "trial";

    public static enum IsOrdered{
        NO,YES,PROCESSING,DELETED
    }

    public static enum IsDelivered{
        PENDING,DELIVERED,PROCESSING,DELETED
    }

    public static enum IsActive{
        NO,YES
    }

}
