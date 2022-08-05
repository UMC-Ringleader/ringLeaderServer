package umc.spring.ringleader.config;

import static org.junit.jupiter.api.Assertions.*;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

class JasyptConfigTest {
	@Test
	public void jasyptTest() {
		String key = "my_jasypt_key";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(key); // 암호화할 때 사용하는 키
		config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘
		config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
		config.setPoolSize("1"); // 인스턴스 pool
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
		config.setStringOutputType("base64"); //인코딩 방식
		encryptor.setConfig(config);

		System.out.println(encryptor.encrypt("EWithMD5AndDESiPBEWSPBthMD5AndDE"));

	}


}