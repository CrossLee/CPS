package com.cemso.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cemso.dao.OnebyoneDao;
import com.cemso.dao.PlaylistDao;
import com.cemso.dao.ProgramDao;
import com.cemso.dao.ResourceDao;
import com.cemso.dao.TemplateDao;
import com.cemso.dao.impl.ActionlogDaoImpl;
import com.cemso.dto.ActionlogDTO;
import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.service.ProgramService;
import com.cemso.util.ConstantUtil;
import com.cemso.util.SequenceUtil;

public class ProgramServiceImpl implements ProgramService {

	private ProgramDao programDao;
	private OnebyoneDao onebyoneDao;
	private ResourceDao resourceDao;
	private TemplateDao templateDao;

	private PlaylistDao playlistDao;

	public PlaylistDao getPlaylistDao() {
		return playlistDao;
	}

	public void setPlaylistDao(PlaylistDao playlistDao) {
		this.playlistDao = playlistDao;
	}

	private Log log = LogFactory.getLog(ProgramServiceImpl.class);

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public ProgramDao getProgramDao() {
		return programDao;
	}

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}

	public OnebyoneDao getOnebyoneDao() {
		return onebyoneDao;
	}

	public void setOnebyoneDao(OnebyoneDao onebyoneDao) {
		this.onebyoneDao = onebyoneDao;
	}

	public boolean addProgram(ProgramDTO program) {
		int indexid = SequenceUtil.getInstance().getNextKeyValue(
				ConstantUtil.SequenceName.PROGRAM_SEQUENCE);
		program.setIndexid(indexid);
		boolean flag1 = programDao.addProgram(program);

		List<OnebyoneDTO> onebyones = program.getOnebyoneList();
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		for (OnebyoneDTO onebyone : onebyones) {
			int indexId = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ONEBYONE_SEQUENCE);
			onebyone.setIndexid(indexId);
			flag2 = flag2 && onebyoneDao.addOnebyone(onebyone);

			// update resource's canbedelete to false
			String resourceName = onebyone.getResourceName();
			flag3 &= resourceDao.updateResource(resourceName, "false");

		}

		// update template's canbedelete to false;
		String templateId = program.getTemplateId();
		flag4 &= templateDao.updateTemplate(templateId, "false");

		if(flag1 && flag2 && flag3 && flag4){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "添加了节目（ID）: "+program.getId()+", 节目名称："+program.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		return flag1 && flag2 && flag3 && flag4;
	}

	// update program canBeDeleteStatus
	private boolean updateProgarmStatus() {
		if (log.isDebugEnabled()) {
			log.debug("start to update program's canBeDelete status");
		}

		boolean flag = true;
		List<ProgramDTO> programList = programDao.queryPrograms();
		List<PlaylistDTO> playlist = playlistDao.queryPlaylists();

		int i = 0;
		int playlistSize = playlist.size();
		boolean restart = false;
		out: for (; i < programList.size();) {
			String programid = programList.get(i).getId();
			int j = 0;
			for (; j < playlistSize; j++) {
				String progrmids = playlist.get(j).getProgramids();
				if (progrmids.contains(programid)) {
					programList.remove(programList.get(i));
					restart = true;
					break;
				}
			}
			if (j == playlistSize) {
				restart = false;
			}
			if (restart) {
				i = 0;
				continue out;
			} else {
				i++;
			}
		}

		if (!programList.isEmpty()) {
			for (ProgramDTO program : programList) {
				flag &= programDao.updateProgram(program.getId(), "true");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("update program's canBeDelete status: " + flag);
		}

		return flag;

	}

	public List<ProgramDTO> getProgramList() {
		boolean flag = updateProgarmStatus();
		if (flag) {
			List<ProgramDTO> programList = programDao.queryPrograms();
			List<OnebyoneDTO> onebyones = onebyoneDao.queryOnebyones();

			for (ProgramDTO program : programList) {
				List<OnebyoneDTO> onebyoneList = new ArrayList<OnebyoneDTO>();
				String programid = program.getId();
				for (OnebyoneDTO onebyoneDTO : onebyones) {
					if (programid.equals(onebyoneDTO.getProgramid())) {
						onebyoneList.add(onebyoneDTO);
					}
				}
				program.setOnebyoneList(onebyoneList);
			}

			return programList;
		} else {
			return null;
		}

	}

	@Override
	public boolean updateProgram(ProgramDTO program) {
		boolean flag1 = programDao.updateProgram(program);
		boolean flag2 = true;
		List<OnebyoneDTO> list = program.getOnebyoneList();
		for (OnebyoneDTO onebyone : list) {
			flag2 &= onebyoneDao.updateOnebyone(onebyone);
		}
		
		if(flag1 && flag2){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "更新了节目（ID）: "+program.getId()+", 节目名称："+program.getName();
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		
		return flag1 && flag2;
	}

	@Override
	public boolean deleteProgram(String id) {
		// delete onebyones
		String programid = programDao.queryProgram(id).getId();

		// delete onebyones witch belong to this program
		boolean flag = onebyoneDao.deleteOnebyone(programid);
		boolean flag2 = programDao.deleteProgram(id);

		if(flag && flag2){
			Integer indexid1 = SequenceUtil.getInstance().getNextKeyValue(
					ConstantUtil.SequenceName.ACTIONLOG_SEQUENCE);
			String time = ActionlogDTO.currentTime();
			String user = ActionlogDTO.username;
			String action = "删除了节目（ID）: "+id;
			String sql = "";
			ActionlogDTO actionlog = new ActionlogDTO(indexid1,time,user,action,sql);
			ActionlogDaoImpl.addActionlog(actionlog);
		}
		
		return flag && flag2;
	}
}
