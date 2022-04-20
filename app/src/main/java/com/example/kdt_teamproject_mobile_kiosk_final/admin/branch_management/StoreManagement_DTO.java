package com.example.kdt_teamproject_mobile_kiosk_final.admin.branch_management;

public class StoreManagement_DTO {

    private String StoreName; // 지점명
    private String StorePhone; // 지점 전화번호
    private String StoreAddress; // 지점 주소

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStorePhone() {
        return StorePhone;
    }

    public void setStorePhone(String storePhone) {
        StorePhone = storePhone;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }

    public StoreManagement_DTO(String storeName, String storePhone, String storeAddress) {
        StoreName = storeName;
        StorePhone = storePhone;
        StoreAddress = storeAddress;
    }

    public StoreManagement_DTO() {

    }
}
