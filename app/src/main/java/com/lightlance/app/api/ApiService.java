package com.lightlance.app.api;

import com.lightlance.app.api.model.BalanceTopUp;
import com.lightlance.app.api.model.LoginUser;
import com.lightlance.app.api.model.Order;
import com.lightlance.app.api.model.PaymentModel;
import com.lightlance.app.api.model.RegisterUser;
import com.lightlance.app.api.responses.CategoriesResponse;
import com.lightlance.app.api.responses.LoginResponse;
import com.lightlance.app.api.responses.OrderListResponse;
import com.lightlance.app.api.responses.OrderResponse;
import com.lightlance.app.api.responses.PackagesResponse;
import com.lightlance.app.api.responses.PaymentResponse;
import com.lightlance.app.api.responses.RegisterResponse;
import com.lightlance.app.api.responses.TopUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * untuk menyimpan jenis method request beserta url masing2 method
 */
public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterUser registerUser);

    @POST("user/topup")
    Call<TopUpResponse> topUp(@Body BalanceTopUp balanceTopUp);

    @POST("order/sendOrder")
    Call<OrderResponse> sendOrder(@Body Order order);

    @POST("order/payOrder")
    Call<PaymentResponse> payOrder(@Body PaymentModel paymentModel);

    @GET("servicing/getCategories")
    Call<CategoriesResponse> getCategories();

    @GET("servicing/getPackages")
    Call<PackagesResponse> getPackages(@Query("categoryId") int categoryId);

    @GET("order/getOrders")
    Call<OrderListResponse> getOrders(@Query("userId") int userId);
}
