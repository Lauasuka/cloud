package io.amoe.account.api.provider;

import io.amoe.cloud.account.dto.PostUserDTO;
import io.amoe.cloud.account.dto.PostUserLoginDTO;
import io.amoe.cloud.account.dto.PutUserDTO;
import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.entity.PageData;
import io.amoe.cloud.entity.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Amoe
 */
public interface ISysUserProvider {
    /**
     * Query system user paging data
     * @param currentPage current page index
     * @param pageSize Current page capacity
     * @return {@link SysUser} List
     */
    @GetMapping("users")
    R<PageData<SysUser>> getUsers(Long currentPage, Long pageSize);

    /**
     * Get users by user id
     * @param id user id
     * @return {@link R<SysUser>}
     */
    @GetMapping("user/{id}")
    R<SysUser> getUserById(@PathVariable Long id);

    /**
     * Adding system users
     * @param dto {@link PostUserDTO}
     * @return {@link R}
     */
    @PostMapping("user")
    R<Void> postUser(@RequestBody @Validated PostUserDTO dto);

    /**
     * modify system user
     * @param dto {@link PutUserDTO}
     * @return {@link R<Void>}
     */
    @PutMapping("user")
    R<Void> putUser(@RequestBody @Validated PutUserDTO dto);

    /**
     * Delete system user by id
     * @param id 系统用户ID
     * @return {@link R<Void>}
     */
    @DeleteMapping("user")
    R<Void> delUser(Long id);

    /**
     * System user login
     * @param dto {@link PostUserLoginDTO}
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return {@link R<SysUser>}
     */
    @PostMapping("user/login")
    R<SysUser> userLogin(@RequestBody @Validated PostUserLoginDTO dto,
                         HttpServletRequest request,
                         HttpServletResponse response);
}
