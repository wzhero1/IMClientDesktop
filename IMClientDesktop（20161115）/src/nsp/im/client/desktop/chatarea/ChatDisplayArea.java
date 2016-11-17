package nsp.im.client.desktop.chatarea;

import static nsp.im.client.desktop.msgitem.TextContentFiller.deleteState;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import nsp.common.func.SafeProc0;
import nsp.im.client.desktop.base.ListView;
import nsp.im.client.desktop.base.StyleConsts;
import nsp.im.client.model.Chat;
import nsp.im.client.model.msg.Message;
import nsp.im.client.model.msg.UserMessage;


@SuppressWarnings("serial")
public class ChatDisplayArea extends ListView {
	private MessageItemFactory msgFactory;
	private DateItemFactory dateFactory;
	private Chat conversation;
	private Date lastDate;
	public static MouseListener deleteListener;
	
	private final WeakHashMap<Component, Object> msgMap;
	private boolean shouldReadAll;
	private AtomicBoolean refreshing;
	
	private SafeProc0 refresher;

	public ChatDisplayArea() {
		setBackground(StyleConsts.main_back);
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		this.msgMap = new WeakHashMap<>();
		shouldReadAll = false;
		refreshing = new AtomicBoolean(false);
		refresher = () -> refresh();
	}

	public void start() {
	}
	
	public void setActive(boolean active) {
		shouldReadAll = active;
		if (shouldReadAll && conversation != null)
			conversation.readAll();
			
	}
	
	public void setConversation(Chat conversation) {
		this.conversation = conversation;
		refresher = new SafeProc0() {
			@Override
			public void invoke() {
				refresh();
			}
		};
		conversation.getUpdateEvent().addListener(refresher);
		if (dateFactory == null) {
			dateFactory = new DefaultDateItemFactory();
		}
		if (msgFactory == null) {
			msgFactory = new DefaultMessageItemFactory(conversation);
		}
		for (Message<?> record : conversation.getMessages()) {
			addMessage(record);
		}
		//20160918添加删除单条记录
		deleteListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				if(e.getButton() != MouseEvent.BUTTON1)
					return;
				else{
					for(Message<?> record : conversation.getMessages())
						if(!deleteState.containsKey(record)){
					    	addMessage(record);
						}
				}
			}
		};
		refresh();
	}
	
	public void addMessage(Message<?> record) {
		if (!(record instanceof UserMessage))
			return;
		if (lastDate == null || record.getTime().getTime() - lastDate.getTime() > 60000) {
			addComponentSilently(dateFactory.makeDateItem(record.getTime()));
		}
		lastDate = record.getTime();
		JComponent msgComp = msgFactory.makeMessageItem((UserMessage<?>) record);
		msgMap.put(msgComp, record.identify());
		//System.out.println(deleteState);
		if (deleteState.containsKey(record)) {
			if(!deleteState.get(record))
                addComponentSilently(msgComp);
		}else
			addComponentSilently(msgComp);
	}
	
	private void refresh() {
//		removeAll();
//		lastDate = null;
//		for (Message<?> record : conversation.getMessages()) {
//			addMessage(record);
//		}
//		revalidate();
		if (!refreshing.compareAndSet(false, true))
			return;
		if (shouldReadAll)
			conversation.readAll();
		HashMap<Object, Message<?>> msgs = new HashMap<>();
		for (Message<?> m : conversation.getMessages())
			msgs.put(m.identify(), m);
		ArrayList<Component> toRemove = new ArrayList<>();
		synchronized (getTreeLock()) {
			for (Component c : getComponents()) {
				Object id = msgMap.get(c);
				if (id != null) {
					if (msgs.get(id) != null)
						msgs.remove(id);
					else
						toRemove.add(c);
				}
			}
		}
		ArrayList<Message<?>> toAdd = new ArrayList<>(msgs.values());
		Collections.sort(toAdd, (m1, m2) -> {
			return m1.getTime().compareTo(m2.getTime());
		});
		for (Component c : toRemove)
			remove(c);
		for (Message<?> m : toAdd)
			addMessage(m);
		refreshing.set(false);
		if (toRemove.isEmpty() && toAdd.isEmpty())
			return;
		revalidate();
	}
}
