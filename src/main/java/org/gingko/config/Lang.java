package org.gingko.config;

import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Lang {

	private static final Logger LOG = LoggerFactory.getLogger(Lang.class);

	private static final String LANG_PREFIX = "lang_";
	private static final String PROPS_EXT = ".properties";

	private static Properties props = new Properties();

	static {

	}

	public static void load() {
		String filePath = PathUtils.getLocalePath() + File.separator + LANG_PREFIX + ServerProperties.langLocale + PROPS_EXT;

		// Load properties
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
			props.load(in);

			paramError = props.getProperty("param.error", paramError);

			operateAddSuccess = props.getProperty("operate.add.success", operateAddSuccess);
			operateAddFailure = props.getProperty("operate.add.failure", operateAddFailure);

			userLoginSuccess = props.getProperty("user.login.success", userLoginSuccess);
			userLoginFailure = props.getProperty("user.login.failure", userLoginFailure);

			LOG.info("Language file load success.");
		} catch (Exception e) {
			LOG.warn("Language file load failed.");
			e.printStackTrace();
		}
	}

	public static String paramError = "Error of param!";

    public static String operateSuccess = "Operate Success!";
    public static String operateFailure = "Operate Failure!";
	public static String operateAddSuccess = "Add Success!";
	public static String operateAddFailure = "Add Failure! Please check your input text is empty!";
    public static String operateEditSuccess = "Edit Success!";
    public static String operateDeleteSuccess = "Delete Success!";
    public static String operateSynCikSuccess = "Operate Success! Delete old cik #{deleteCount}, Add new cik #{addCount}";
    public static String operateOutOfIdentity = "Operate Failure! Your identity lack!";

	public static String userLoginSuccess = "Login Success!";
	public static String userLoginFailure = "Login Failure! Please check your account and password!";

    public static String userExisted = "Failure! The account already existed!";
    public static String userNotExisted = "Failure! The user not existed!";
    public static String identityExisted = "Failure! The identity with menuId already existed!";
    public static String settingTypeExisted = "Failure! The form type already existed!";

}
