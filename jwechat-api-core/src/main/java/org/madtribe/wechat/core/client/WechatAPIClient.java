package org.madtribe.wechat.core.client;

import java.io.InputStream;
import java.util.Optional;

import javax.inject.Inject;

import org.madtribe.wechat.core.client.accesstoken.AccessToken;
import org.madtribe.wechat.core.client.errors.WeChatResponseError;
import org.madtribe.wechat.core.client.messages.CustomerServiceMessage;
import org.madtribe.wechat.core.client.messages.MediaType;
import org.madtribe.wechat.core.client.responses.MediaUploadResponse;
import org.madtribe.wechat.core.configuration.WeChatConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WechatAPIClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHttpRequestUtils.class);
	@Inject
	private IHttpRequestUtils httpClient;
	
	@Inject
	private WeChatConfiguration config;	

	public Optional<AccessToken> requestNewAccessToken() throws WeChatResponseError {
		String tokenUrlforAppIdAndAppSecret = config.getWechatURLsConfig().getTokenUrlforAppIdAndAppSecret();
		String url = String.format(tokenUrlforAppIdAndAppSecret, config.getWeChatAppId(), config.getWeChatAppSecret());
		Optional<AccessToken> accesToken = httpClient.getAsObject(url,AccessToken.class);
		return accesToken;
	}

	public void sendMessage(Optional<AccessToken> accessToken, CustomerServiceMessage message) {
		
		if (accessToken.isPresent()){
			String sendMessageUrlforAccessToken = config.getWechatURLsConfig().getSendMessageUrlforAccessToken();
			String url = String.format(sendMessageUrlforAccessToken, accessToken.get().getAccessTokenString());
			httpClient.postObject(url,message);
		} else {
			LOGGER.warn("No Access token provided. Giving up.");
		}
	}
	
	public Optional<MediaUploadResponse> uploadTemporaryMedia(Optional<AccessToken> accessToken, InputStream inputStream, MediaType type) throws WeChatResponseError{
		String addMaterialUrlForAccessTokenAndType = config.getWechatURLsConfig().getAddMaterialforAccessTokenAndType();
		String url = String.format(addMaterialUrlForAccessTokenAndType, accessToken.get().getAccessTokenString(), type.name() );
		
		Optional<MediaUploadResponse> response = httpClient.postFile( url, inputStream, "media", MediaUploadResponse.class);
		return response;
	}
	
}