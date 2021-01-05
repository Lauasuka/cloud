package io.amoe.cloud.account.feign;

import io.amoe.account.api.provider.ISysUserProvider;
import io.amoe.cloud.account.dto.PostUserDTO;
import io.amoe.cloud.account.dto.PostUserLoginDTO;
import io.amoe.cloud.account.dto.PutUserDTO;
import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.PageData;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Amoe
 * @date 2020/4/16 17:02
 */
@FeignClient(value = "cloud-account", fallback = ISysUserFeign.SysUserFeignFallback.class)
public interface ISysUserFeign extends ISysUserProvider {

    @Component
    class SysUserFeignFallback extends AbstractProvider implements ISysUserFeign{

        @Override
        public R<PageData<SysUser>> getUsers(Long currentPage, Long pageSize) {
            throw new BizException(BizResponseStatus.NOT_FOUND);
        }

        @Override
        public R<SysUser> getUserById(Long id) {
            throw new BizException(BizResponseStatus.NOT_FOUND);
        }

        @Override
        public R<Void> postUser(PostUserDTO dto) {
            throw new BizException(BizResponseStatus.ERROR);
        }

        @Override
        public R<Void> putUser(PutUserDTO dto) {
            throw new BizException(BizResponseStatus.ERROR);
        }

        @Override
        public R<Void> delUser(Long id) {
            throw new BizException(BizResponseStatus.ERROR);
        }

        @Override
        public R<SysUser> userLogin(PostUserLoginDTO dto, HttpServletRequest request, HttpServletResponse response) {
            throw new BizException(BizResponseStatus.ERROR);
        }
    }
}
