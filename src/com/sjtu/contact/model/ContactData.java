package com.sjtu.contact.model;

import java.util.ArrayList;
import java.util.List;

import com.sjtu.contact.pojo.Department;
import com.sjtu.contact.pojo.Institution;
import com.sjtu.contact.pojo.Organization;
import com.sjtu.contact.pojo.Response;
import com.sjtu.contact.service.LocationService;
import com.sjtu.contact.service.QueryFilter;
import com.sjtu.contact.util.Logger;

public class ContactData {
	
	private static ContactData instance;
	private DataCache cache;
	private LocationService locationService;
	
	private ContactData()
	{
		cache = new DataCache();
		locationService = LocationService.getInstance();
		
	};
	
	public void clear()
	{
		this.cache.clear();
	}
	
	public static ContactData getInstance()
	{
		if(instance == null)
			instance = new ContactData();
		
		return instance;
	}
	
	
	public List getOrganizations()
	{
		List oList = cache.getOrganizations();
		
		if( oList.isEmpty() )
		{
			// to get remote data
			Logger.log("getOrgs:retriving remove data...");
			Response r = locationService.queryOrgs();
			if(r.getErrorCode() == 0)
			{
				oList = (List) r.getObj();
				cache.doCache(oList);
			}
			else
			{
				Logger.log("Error,"+r.getErrorMsg());
			}
			
		}
		
		return oList;
	}
	
	
	public List getInstitutions(int orgId)
	{
		List iList = cache.getInstitutions(orgId);
		
		if( iList.isEmpty() )
		{
			QueryFilter filter = new QueryFilter();
			filter.AddFilter("orgid", orgId+"");
			
			Response r = locationService.queryInsts(filter);
			
			if(r.getErrorCode() == 0)
			{
				iList = (List) r.getObj();
				Logger.log("response got, insts count:"+iList.size());
				
				cache.doCache(iList);
			}
			else
			{
				Logger.log("Error,"+r.getErrorMsg());
			}
			
		}
		
		
		return iList;
	}
	
	public List getDepartments(int instId)
	{
		List dList = cache.getDepartments(instId);
		
		if( dList.isEmpty() )
		{
			QueryFilter filter = new QueryFilter();
			filter.AddFilter("instid", instId+"");
			
			Response r = locationService.queryDepts(filter);
			if(r.getErrorCode() == 0)
			{
				dList = (List) r.getObj();
				cache.doCache(dList);
			}
			else
			{
				Logger.log("Error,"+r.getErrorMsg());
			}
		}
		
		return dList;
	}
	
	
	private class DataCache
	{
		public List<Organization> orgs;
		public List<Institution> insts;
		public List<Department> depts;
		
		public DataCache()
		{
			orgs = new ArrayList<Organization>();
			insts = new ArrayList<Institution>();
			depts = new ArrayList<Department>();
		}
		
		public void clear() {
			orgs = new ArrayList<Organization>();
			insts = new ArrayList<Institution>();
			depts = new ArrayList<Department>();
		}

		public List<Organization> getOrganizations()
		{
			return orgs;
		}
		
		public List<Institution> getInstitutions(int orgId)
		{
			ArrayList<Institution> iList = new ArrayList<Institution>();
			
			for( Institution inst: insts )
			{
				if(inst.getOrgId() == orgId)
					iList.add(inst);
			}
			
			return iList;
		}
		
		public List<Department> getDepartments(int instId)
		{
			ArrayList<Department> dList = new ArrayList<Department>();
			
			for( Department dep: depts )
			{
				if( dep.getInstId() == instId )
					dList.add(dep);
			}
			
			return dList;
		}
		
		public void doCache(List objList)
		{
			for(Object ob: objList)
			{
				if( ob instanceof Department)
				{
//					depts.add( (Department)ob);
					addUnique( (Department)ob );
				}
				else if(ob instanceof Institution)
				{
//					insts.add( (Institution)ob );
					addUnique( (Institution)ob);
				}
				else if(ob instanceof Organization)
				{
//					orgs.add( (Organization)ob );
					addUnique( (Organization)ob);
				}
			}
		}
		
		private void addUnique( Department dept )
		{
			boolean isExist = false;
			for(Department d:depts)
			{
				if( d.getId() == dept.getId() )
				{
					isExist = true;
					break;
				}
					
			}
			if( !isExist )
			{
				depts.add(dept);
			}
		}
		
		private void addUnique( Institution inst )
		{
			boolean isExist = false;
			for(Institution i:insts)
			{
				if( i.getId() == inst.getId() )
				{
					isExist = true;
					break;
				}
					
			}
			if( !isExist )
			{
				insts.add(inst);
			}
		}
		
		private void addUnique( Organization org )
		{
			boolean isExist = false;
			for(Organization o:orgs)
			{
				if( o.getId() == org.getId() )
				{
					isExist = true;
					break;
				}
					
			}
			if( !isExist )
			{
				orgs.add(org);
			}
		}
	}
}
