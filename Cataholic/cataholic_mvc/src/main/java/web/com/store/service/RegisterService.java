package web.com.store.service;

import web.com.store.model.MemberBean;

public interface RegisterService {
    boolean isIdExists(String id);

    int insertMember(MemberBean memberBean); // 新增會員

    int updateMember(String memberId, String column, String updateValue); // 修改會員

    int deleteMember(MemberBean memberBean); // 刪除會員

    MemberBean queryByMemberId(String memberId); // 查詢會員（id）

    MemberBean checkIdPassword(String memberId, String memberPassword);
}
