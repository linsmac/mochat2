package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.dao.ChatRoomDAO;
import com.example.dao.FriendDAO;
import com.example.dao.UserDAO;
import com.example.vo.ChatRoomVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ChatRoomService {
	private final ChatRoomDAO chatRoomDAO;
	private final UserDAO userDAO;
	private final FriendDAO friendDAO;

	public ChatRoomService(UserDAO userDAO, ChatRoomDAO chatRoomDAO, FriendDAO friendDAO) {
		this.userDAO = userDAO;
		this.chatRoomDAO = chatRoomDAO;
		this.friendDAO = friendDAO;
	}

//	public String getHistoryText(String roomId) {
//		List<ChatRoomVO> chatRoomVo = chatRoomDAO.getUserChatList(roomId);
//		String text = null;
//		for(ChatRoomVO chatRoom : chatRoomVo) {
//			text = chatRoom.getText();
//		}
//		return text;
//	}

	@SuppressWarnings("deprecation")
	public ObjectNode getHistoryText(String roomId) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonResponse = objectMapper.createObjectNode();

	    try {
	        List<ChatRoomVO> chatRoomVoList = chatRoomDAO.getUserChatList(roomId);
	        ArrayNode chatRoomsArray = objectMapper.createArrayNode();

	        for (ChatRoomVO chatRoomVo : chatRoomVoList) {
	            if (chatRoomVo.getText() != null) { // 检查文本内容是否为空
	                JsonNode chatRoomText = objectMapper.readTree(chatRoomVo.getText());

	                for (JsonNode chatroomtext : chatRoomText) {
	                    String timestamp = chatroomtext.get("timestamp").asText();
	                    long timestampInMillis = Long.parseLong(timestamp) * 1000L;
	                    Date date = new Date(timestampInMillis);
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	                    String formattedDate = dateFormat.format(date);
	                    // 更新消息对象的时间戳
	                    ((ObjectNode) chatroomtext).put("timestamp", formattedDate);
	                }

	                chatRoomsArray.add(chatRoomText);
	            }
	        }

			jsonResponse.put("chatRooms", chatRoomsArray);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.put("error", "Failed to convert to JSON");
		}

		return jsonResponse;
	}

	public String updateOrInsertText(JsonNode requestBody) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String returnText = null;

		JsonNode history = requestBody.get("historyText");

		if (history != null) {

			String historyText = requestBody.get("historyText").asText();
			String newText = requestBody.get("text").asText();

			try {
				JSONArray oldJSONArray = new JSONArray(historyText);
				JSONArray newJSONArray = new JSONArray(newText);

				for (int i = 0; i < oldJSONArray.length(); i++) {
					JSONObject obj = oldJSONArray.getJSONObject(i);
					String timestampStr = obj.getString("timestamp");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date date = sdf.parse(timestampStr);
					long timestamp = date.getTime()/1000;
					obj.put("timestamp", timestamp);
				}

				for (int i = 0; i < newJSONArray.length(); i++) {
					JSONObject newObj = newJSONArray.getJSONObject(i);
					oldJSONArray.put(newObj);
				}                

				String combined = oldJSONArray.toString();
                JsonNode combinedNode = mapper.readTree(combined);
                ((ObjectNode) requestBody).set("combinedText", combinedNode);
                returnText = chatRoomDAO.update(requestBody);

			} catch (ParseException e) {
				e.printStackTrace();
				returnText = "01";
				return returnText;
			}

		}else {
			returnText = chatRoomDAO.Insert(requestBody);
		}

		return returnText;
	}

}
