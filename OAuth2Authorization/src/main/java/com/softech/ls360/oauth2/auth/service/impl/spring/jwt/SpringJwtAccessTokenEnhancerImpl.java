package com.softech.ls360.oauth2.auth.service.impl.spring.jwt;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.softech.ls360.oauth2.auth.model.JwtAccessToken;
import com.softech.ls360.oauth2.auth.service.spring.jwt.SpringJwtAccessTokenConverter;
import com.softech.ls360.oauth2.auth.service.spring.jwt.SpringJwtAccessTokenEnhancer;

@Service
public class SpringJwtAccessTokenEnhancerImpl implements SpringJwtAccessTokenEnhancer  {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SpringJwtAccessTokenConverter tokenConverter;
	private JsonParser objectMapper = JsonParserFactory.create();
	private String verifierKey = new RandomValueStringGenerator().generate();
	private Signer signer = new MacSigner(verifierKey);
	private String signingKey = verifierKey;
	private SignatureVerifier verifier;
	
	@PostConstruct
	public void init() {
		setSigningKey("123456");
		SignatureVerifier verifier = new MacSigner(verifierKey);
		try {
			verifier = new RsaVerifier(verifierKey);
		}
		catch (Exception e) {
			logger.warn("Unable to create an RSA verifier from verifierKey (ignoreable if using MAC)");
		}
		// Check the signing and verification keys match
		if (signer instanceof RsaSigner) {
			byte[] test = "test".getBytes();
			try {
				verifier.verify(test, signer.sign(test));
				logger.info("Signing and verification RSA keys match");
			}
			catch (InvalidSignatureException e) {
				logger.error("Signing and verification RSA keys do not match");
			}
		} else if (verifier instanceof MacSigner) {
			// Avoid a race condition where setters are called in the wrong order. Use of
			// == is intentional.
			Assert.state(this.signingKey == this.verifierKey, "For MAC signing you do not need to specify the verifier key separately, and if you do it must match the signing key");
		}
		this.verifier = verifier;
		
	}
	
	/**
	 * Sets the JWT signing key. It can be either a simple MAC key or an RSA key. RSA keys
	 * should be in OpenSSH format, as produced by <tt>ssh-keygen</tt>.
	 * 
	 * @param key the key to be used for signing JWTs.
	 */
	public void setSigningKey(String key) {
		Assert.hasText(key);
		key = key.trim();

		this.signingKey = key;

		if (isPublic(key)) {
			signer = new RsaSigner(key);
			logger.info("Configured with RSA signing key");
		} else {
			// Assume it's a MAC key
			this.verifierKey = key;
			signer = new MacSigner(key);
		}
	}
	
	/**
	 * @return true if the key has a public verifier
	 */
	private boolean isPublic(String key) {
		return key.startsWith("-----BEGIN");
	}
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		JwtAccessToken result = new JwtAccessToken(accessToken);
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		//String tokenId = result.getValue();
		//if (!info.containsKey(TOKEN_ID)) {
			//info.put(TOKEN_ID, tokenId);
		//}
		//else {
			//tokenId = (String) info.get(TOKEN_ID);
		//}
		result.setAdditionalInformation(info);
		result.setValue(encode(result, authentication));
		OAuth2RefreshToken refreshToken = result.getRefreshToken();
		if (refreshToken != null) {
			DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
			encodedRefreshToken.setValue(refreshToken.getValue());
			// Refresh tokens do not expire unless explicitly of the right type
			encodedRefreshToken.setExpiration(null);
			try {
				//Map<String, Object> claims = objectMapper.parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
				//if (claims.containsKey(TOKEN_ID)) {
					//encodedRefreshToken.setValue(claims.get(TOKEN_ID).toString());
				//}
			}
			catch (IllegalArgumentException e) {
			}
			Map<String, Object> refreshTokenInfo = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
			///refreshTokenInfo.put(TOKEN_ID, encodedRefreshToken.getValue());
			//refreshTokenInfo.put(ACCESS_TOKEN_ID, tokenId);
			encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);
			DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(encode(encodedRefreshToken, authentication));
			if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
				Date expiration = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration();
				encodedRefreshToken.setExpiration(expiration);
				token = new DefaultExpiringOAuth2RefreshToken(encode(encodedRefreshToken, authentication), expiration);
			}
			result.setRefreshToken(token);
		}
		
		String encodeTokenValue = result.getValue();
		JwtAccessToken encodedResult = new JwtAccessToken(encodeTokenValue);
		
		return encodedResult;
	}
	
	protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		String content;
		try {
			content = objectMapper.formatMap(tokenConverter.convertAccessToken(accessToken, authentication));
		}
		catch (Exception e) {
			throw new IllegalStateException("Cannot convert access token to JSON", e);
		}
		
		//logger.info(content);
		String token = JwtHelper.encode(content, signer).getEncoded();
		return token;
	}

}
