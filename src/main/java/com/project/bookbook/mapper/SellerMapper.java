package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bookbook.domain.dto.MypageSellerDTO;
import com.project.bookbook.domain.dto.OrderUpdateDTO;
import com.project.bookbook.domain.dto.SellerIndexDTO;
import com.project.bookbook.domain.dto.SellerInventoryDTO;
import com.project.bookbook.domain.dto.SellerUpdateDTO;
import com.project.bookbook.domain.dto.SellerOrderDTO;

@Mapper
public interface SellerMapper {

	@Select("select * from seller where seller_id = #{sellerId}")
	MypageSellerDTO findProcess(long sellerId);

	@Update("update seller set user_name = #{userName}, business_num = #{businessNum}, bank = #{bank}, account = #{account}, account_holder = #{accountHolder}, userrrn= #{userRRN}, email = #{email}, phone_number = #{phoneNumber}, birth_date = #{birthDate}, address = #{address} where seller_id = #{sellerId}")
	void updateId(SellerUpdateDTO dto);

	@Select("select * from book where publisher = #{sellerName} order by book_num desc")
	List<SellerInventoryDTO> findAll(@Param("sellerName") String sellerName);

	@Select("SELECT " +
            "    uod.book_num AS bookNum, " +
            "    u.user_id AS userId, " +
            "    u.user_name AS userName, " +
            "    uo.coupon_num AS couponNum, " +
            "    c.coupon_name AS couponName, " +
            "    uo.order_date AS orderDate, " +
            "    uo.paid_amount AS paidAmount, " +
            "    uo.order_status AS orderStatus, " +
            "    uod.order_detail_num AS orderDetailNum, " +
            "    uod.merchant_uid AS merchantUid, " +
            "    uod.order_cnt AS orderCnt " +
            "FROM " +
            "    book b " +
            "    INNER JOIN user_orders_detail uod ON b.book_num = uod.book_num " +
            "    INNER JOIN user_orders uo ON uod.merchant_uid = uo.merchant_uid " +
            "    INNER JOIN user u ON uo.user_id = u.user_id " +
            "    LEFT JOIN coupon c ON uo.coupon_num = c.coupon_num " +
            "WHERE " +
            "    b.publisher = #{sellerName} " +
            "    AND uo.order_status <> 0")
	List<SellerOrderDTO> findOrder(@Param("sellerName") String sellerName);

	@Update("update user_orders set merchant_uid = #{merchantUid}, order_status = 2 where merchant_uid = #{merchantUid}")
	void updateOrder(long merchantUid);

	@Select("select * from book order by book_num desc")
	List<SellerIndexDTO> find();

	@Delete("delete from book where book_num = #{bookNum};")
	void deleteBook(@Param("bookNum") long bookNum);


}
