package com.sjtu.contact.service;

import com.sjtu.contact.pojo.User;
import com.sjtu.contact.util.Logger;

public class GlobalVar {
	
	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		Logger.log("set currentuser to user "+currentUser.getDisplayName());
		GlobalVar.currentUser = currentUser;
	}
	
}
