package com.cemso.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.OnebyoneDao;
import com.cemso.dao.ResourceDao;
import com.cemso.dao.impl.ActionlogDaoImpl;
import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.ResourceDTO;
import com.cemso.service.ResourceService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;

public class ResourceServiceImpl implements ResourceService {

	private ResourceDao resourceDao;
	private OnebyoneDao onebyoneDao;

	private Log log = LogFactory.getLog(ResourceServiceImpl.class);

	public OnebyoneDao getOnebyoneDao() {
		return onebyoneDao;
	}

	public void setOnebyoneDao(OnebyoneDao onebyoneDao) {
		this.onebyoneDao = onebyoneDao;
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	/**
	 * update the delete flag
	 * 
	 * @return
	 */
	private boolean updateResourceStatus() {
		if (log.isDebugEnabled()) {
			log.debug("start to update resource's canBeDelete status...");
		}
		boolean flag = true;
		List<ResourceDTO> resourceList = resourceDao.queryResources();
		List<OnebyoneDTO> onebyoneList = onebyoneDao.queryOnebyones();
		int i = 0;
		boolean restart = false;
		int onebyoneSize = onebyoneList.size();
		out: for (; i < resourceList.size();) {
			String name = resourceList.get(i).getResourceName();
			int j = 0;
			for (; j < onebyoneSize; j++) {
				String resourceName = onebyoneList.get(j).getResourceName();
				if (resourceName.equals(name)) {
					resourceList.remove(resourceList.get(i));
					restart = true;
					break;
				}
			}
			if (j == onebyoneSize) {
				restart = false;
			}
			if (restart) {
				i = 0;
				continue out;
			} else {
				i++;
			}
		}
		if (!resourceList.isEmpty()) {
			for (ResourceDTO resource : resourceList) {
				flag &= resourceDao.updateResource(resource.getResourceName(),
						"true");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("update resource's canBeDelete status: " + flag);
		}
		return flag;
	}

	public List<ResourceDTO> getResourceList() {
		boolean flag = updateResourceStatus();
		if (flag) {
			List<ResourceDTO> resourceList = resourceDao.queryResources();
			return resourceList;
		} else {
			return null;
		}
	}

	@Override
	public boolean AddResource(ResourceDTO resource) {
		int indexid = SequenceUtil.getInstance().getNextKeyValue(
				ConstantUtil.SequenceName.DEVICE_SEQUENCE);
		resource.setIndexid(indexid);
		boolean flag = resourceDao.addResource(resource);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "添加了资源（ID）: "+resource.getId()+", 资源名称："+resource.getResourceName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	@Override
	public boolean deleteResource(int indexid) {
		boolean flag = resourceDao.deleteResource(indexid);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了资源（indexid）: "+indexid;
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	@Override
	public boolean deleteResource(String id) {
		boolean flag = resourceDao.deleteResource(id);
		if(flag){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了资源（ID）: "+id;
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag;
	}

	@Override
	public ResourceDTO queryResource(int indexid) {
		return resourceDao.queryResource(indexid);
	}
}
