package com.nix.managecafe.util;

import com.nix.managecafe.model.Bill;
import com.nix.managecafe.model.BillDetail;
import com.nix.managecafe.model.Menu;
import com.nix.managecafe.model.User;
import com.nix.managecafe.payload.UserSummary;
import com.nix.managecafe.payload.response.BillDetailResponse;
import com.nix.managecafe.payload.response.BillResponse;
import com.nix.managecafe.payload.response.MenuResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static MenuResponse mapMenuToMenuResponse(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setCost(menu.getCost());
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setDescription(menuResponse.getDescription());
        menuResponse.setOriginCost(menu.getOriginCost());
        return menuResponse;
    }
    public static BillDetailResponse mapBillDetailToBillDetailResponse(BillDetail billDetail) {
        BillDetailResponse billDetailResponse = new BillDetailResponse();
        billDetailResponse.setId(billDetail.getId());
        billDetailResponse.setCount(billDetail.getCount());
        billDetailResponse.setMenu(mapMenuToMenuResponse(billDetail.getMenu()));
        return billDetailResponse;
    }
    public static BillResponse mapBillToBillResponse(Bill bill, User user) {
        BillResponse billResponse = new BillResponse();
        billResponse.setId(bill.getId());
        billResponse.setTable(bill.getTable().getName());
        billResponse.setPayment(bill.getPayment());
        billResponse.setCreateDate(bill.getCreatedAt());
        List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(
                ModelMapper::mapBillDetailToBillDetailResponse
        ).collect(Collectors.toList());
        billResponse.setUser(new UserSummary(user.getId(), user.getUsername(), user.getName(), user.getEmail()));
        billResponse.setBillDetails(billDetailResponses);
        return billResponse;
    }
}
