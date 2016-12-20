package org.madtribe.wechat.core.messageparsers.wc3;

import java.util.Optional;

import org.madtribe.wechat.core.constants.HeaderFieldNames;
import org.madtribe.wechat.core.messageparsers.InboundPayloadParser;
import org.madtribe.wechat.core.messages.TextMessage;
import org.madtribe.wechat.core.messages.inbound.request.InboundPayload;
import org.madtribe.wechat.core.streamparsers.MessageParsingException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class InboundTextMessageParser extends AbstractPayloadParser  {
	@Override
	public Optional<InboundPayload> parse(Element content) throws MessageParsingException {
		TextMessage message = new TextMessage(getElementByName(content, HeaderFieldNames.Content.name()).getTextContent());
		return Optional.of(message);
	}
	
}
