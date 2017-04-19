package whiteboard;

import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/server/v1/whiteboard")
public class WhiteboardSocket {
	
	private static Vector<Session> sessionVector = new Vector<Session>();

	public WhiteboardSocket() {
		// TODO Auto-generated constructor stub
	}
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Opening session: " + session.getId());
		sessionVector.add(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Got message: " + message);
		try {
			for (Session s : sessionVector) {
				if (session.equals(s)) continue;
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}

	@OnClose
	public void close(Session session) {
		System.out.println("Closing session: " + session.getId());
		sessionVector.remove(session);
	}

	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
	}

}
