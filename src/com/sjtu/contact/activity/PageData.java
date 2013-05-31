package com.sjtu.contact.activity;

import java.util.ArrayList;
import java.util.List;

import com.sjtu.contact.pojo.Contact;

public class PageData {
	
	public final static int PAGE_SIZE = 10;
	public static List<Contact> contacts = new ArrayList<Contact>();
	private static Contact selectedContact;
	
	public static void setSelectedContact(Contact selectedContact) {
		PageData.selectedContact = selectedContact;
	}

	public static Contact getSelectedContact() {
		return selectedContact;
	}
	
	public static void setContacts(List<Contact> _contacts)
	{
		
		contacts = _contacts;
	}
	
	public static List<Contact> getContacts()
	{
		return contacts;
	}
	
	public static void appendContacts(List<Contact> _contacts)
	{
		contacts.addAll(_contacts);
	}
	
	public static void clear()
	{
		contacts = new ArrayList<Contact>();
		selectedContact = null;
	}	
}
