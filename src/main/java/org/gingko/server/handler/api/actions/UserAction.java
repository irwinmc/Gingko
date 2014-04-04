package org.gingko.server.handler.api.actions;

import com.google.gson.Gson;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.sys.User;
import org.gingko.app.persist.mapper.sys.UserMapper;
import org.gingko.config.Lang;
import org.gingko.context.AppContext;
import org.gingko.server.handler.api.vo.ExtMessage;
import org.gingko.server.handler.api.vo.ExtPagingData;

import java.util.List;

/**
 * Created by TangYing on 14-3-20.
 */
public enum UserAction {

    INSTANCE;

    private static UserMapper userMapper = (UserMapper) AppContext.getBean(PersistContext.USER_MAPPER);

    /**
     * User login
     *
     * @return
     */
    public String login(String account, String password) {
            User user = userMapper.login(account, password);
            ExtMessage message = new ExtMessage(false, Lang.userLoginFailure);
            if (user != null) {
                message.setSuccess(true);
                message.setMsg(Lang.userLoginSuccess);
                message.setAccount(user.getAccount());
                message.setIdentity(user.getIdentity());
        }
        return new Gson().toJson(message);
    }

    /**
     * User laod
     *
     * @param start
     * @param limit
     * @return
     */
    public String load(String start, String limit) {
        int l = 0, o = 0;      // limit & offset
        try {
            l = Integer.valueOf(limit);
            o = Integer.valueOf(start);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        List<User> users = userMapper.selectByPage(l, o);
        int totalCount = userMapper.selectTotalCount();

        ExtPagingData<User> items = new ExtPagingData<User>(true, totalCount, users);
        return new Gson().toJson(items);
    }

    /**
     * User add
     *
     * @param account
     * @param password
     * @param identity
     * @return
     */
    public String add(String account, String password, String name, String identity, String groupId) {
        int iId = 0, gId = 0;
        try {
            iId = Integer.valueOf(identity);
            gId = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check account existed.
        User user = userMapper.selectByAccount(account);
        if (user != null) {
            ExtMessage message = new ExtMessage(false, Lang.userExisted);
            return new Gson().toJson(message);
        }

        user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setIdentity(iId);
        user.setIdentity(gId);
        userMapper.insert(user);

        ExtMessage message = new ExtMessage(true, Lang.operateAddSuccess);
        return new Gson().toJson(message);

    }

    /**
     * User eidt
     *
     * @param account
     * @param password
     * @param identity
     * @return
     */
    public String edit( String account, String password, String name, String identity, String groupId) {
        int iId = 0, gId = 0;
        try {
            iId = Integer.valueOf(identity);
            gId = Integer.valueOf(groupId);
        } catch (NumberFormatException e) {
            return new Gson().toJson(new ExtMessage(false, Lang.paramError));
        }

        // Check account existed.
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            ExtMessage message = new ExtMessage(false, Lang.userNotExisted);
            return new Gson().toJson(message);
        }
        user.setAccount(account);
        user.setPassword(password);
        user.setName(name);
        user.setIdentity(iId);
        user.setGroupId(gId);
        userMapper.update(user);

        ExtMessage message = new ExtMessage(true, Lang.operateEditSuccess);
        return new Gson().toJson(message);
    }

    /**
     * User delete
     *
     * @param account
     * @return
     */
    public String deleteByAccount(String account) {
        userMapper.deleteByAccount(account);
        ExtMessage message = new ExtMessage(true, Lang.operateDeleteSuccess);
        return new Gson().toJson(message);
    }
}