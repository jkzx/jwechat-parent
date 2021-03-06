package org.madtribe.wechat.core.jersey.entityproviders;

import org.madtribe.wechat.core.messages.inbound.request.InboundRequest;
import org.madtribe.wechat.core.streamparsers.MessageParsingException;
import org.madtribe.wechat.core.streamparsers.WeChatInboundMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Created by paul.smout on 02/04/2016.
 * 
 * 
 */
public class WeChatInboundRequestEntityProvider implements MessageBodyReader<InboundRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatInboundRequestEntityProvider.class);
    private WeChatInboundMessageParser weChatInboundMessageParser;

    @Inject
    public WeChatInboundRequestEntityProvider(WeChatInboundMessageParser weChatInboundMessageParser) {
        this.weChatInboundMessageParser = weChatInboundMessageParser;
    }


    @Override
    public boolean isReadable(Class<?> aClass,
                              Type type,
                              Annotation[] annotations,
                              MediaType mediaType) {
        boolean readable = false;
        if (type == InboundRequest.class){
            readable = true;
        }
        return readable;
    }

    @Override
	public InboundRequest readFrom(Class<InboundRequest> inboundRequestClass,
                                   Type type,
                                   Annotation[] annotations,
                                   MediaType mediaType,
                                   MultivaluedMap<String,
                                   String> stringStringMultivaluedMap,
                                   InputStream inputStream) throws IOException, WebApplicationException {



        try {
            return weChatInboundMessageParser.parse(inputStream).orElse(null);
        } catch (MessageParsingException e) {
            LOGGER.error("Message Parsing Exception", e);
            throw new WebApplicationException(e);

        }

    }



}
