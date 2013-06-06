package com.cemso.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipOutputStream;
import org.jdom.JDOMException;

import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.dto.UserDTO;
import com.cemso.service.PlaylistService;
import com.cemso.service.ProgramService;
import com.cemso.util.FileOperationTool;
import com.cemso.util.XmlOperationTool;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PlaylistAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private List<PlaylistDTO> playlistList;
	private List<ProgramDTO> programList;

	private PlaylistService playlistService;
	private ProgramService programService;

	private PlaylistDTO playlistObject;

	private String fileName;
	private String path;

	private static final Log log = LogFactory
			.getLog(com.cemso.action.PlaylistAction.class);

	public String getPlaylist() {
			try {
				if (log.isDebugEnabled()) {
					log.debug("PlaylistAction -> getPlaylist()");
				}
				playlistList = playlistService.getPlaylist();
				programList = programService.getProgramList();
				return "playlist";
			} catch (Exception e) {
				if(log.isErrorEnabled()){
					log.error(e.getMessage());
				}
				e.printStackTrace();
				return ERROR;
			}
	}

	// add playlist
	public String addPlaylist() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("PlaylistAction -> addPlaylist()");
			}

			ActionContext context = ActionContext.getContext();
			Map<String, Object> session = context.getSession();

			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String name = null;
			String remark = null;
			String options = request.getParameter("options"); // program_1329830947802,program_1329841699056,program_1329923728496,

			try {
				name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
				remark = URLDecoder.decode(request.getParameter("remark"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				if(log.isErrorEnabled()){
					log.error(e.getMessage());
				}
				e.printStackTrace();
			}
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String startnow = request.getParameter("startnow");

			PlaylistDTO playlist = new PlaylistDTO();
			playlist.setId("playlist_" + System.currentTimeMillis());
			playlist.setName(name);
			playlist.setCreateby(((UserDTO) session.get("user")).getId());
			playlist.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(System.currentTimeMillis()));
			playlist.setRemark(remark);
			playlist.setStartnow(Boolean.valueOf(startnow));
			playlist.setStarttime(starttime);
			playlist.setEndtime(endtime);

			List<ProgramDTO> programs = new ArrayList<ProgramDTO>();
			String[] optionsArray = options.split(",");
			
			playlist.setProgramids(options);
			
			int size = optionsArray.length;
			for (int i = 0; i < size; i++) {
				ProgramDTO program = new ProgramDTO();
				program.setId(optionsArray[i]);
				program.setIndexNum(String.valueOf(i));
				programs.add(program);
			}

			playlist.setProgramList(programs);
			return playlistService.addPlaylist(playlist) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}

	}

	// get edit playlist object
	public String editGetPlaylist() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String id = request.getParameter("id");
			playlistList = playlistService.getPlaylist();
			for (PlaylistDTO playlist : playlistList) {
				if (playlist.getId().equals(id)) {
					playlistObject = playlist;
				}
			}
			return "playlistObject";
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete playlist
	public String delPlaylist() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			// playlist id
			String id = request.getParameter("id");
			return playlistService.deletePlaylist(id) ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// delete selected playlist
	public String delSelectedPlaylist() {
		try {
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			// playlist ids
			String ids = request.getParameter("ids");

			String[] id = ids.split(",");
			int size = id.length;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				flag = flag && playlistService.deletePlaylist(id[i]);
			}

			return flag ? SUCCESS : ERROR;
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	// edit then save playlist
	public String editPlaylist() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("PlaylistAction -> editPlaylist()");
			}

			ActionContext context = ActionContext.getContext();
			Map<String, Object> session = context.getSession();

			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);

			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String remark = request.getParameter("remark");
			String startnow = request.getParameter("startnow");
			String options = request.getParameter("options");

			PlaylistDTO playlist = new PlaylistDTO();
			playlist.setId(id);
			playlist.setName(name);
			playlist.setCreateby(((UserDTO) session.get("user")).getId());
			playlist.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(System.currentTimeMillis()));
			playlist.setRemark(remark);
			playlist.setStartnow(Boolean.valueOf(startnow));
			playlist.setStarttime(starttime);
			playlist.setEndtime(endtime);

			List<ProgramDTO> programs = new ArrayList<ProgramDTO>();
			String[] optionsArray = options.split(",");
			int size = optionsArray.length;
			for (int i = 0; i < size; i++) {
				ProgramDTO program = new ProgramDTO();
				program.setId(optionsArray[i]);
				program.setIndexNum(String.valueOf(i));
				programs.add(program);
			}

			playlist.setProgramList(programs);
			XmlOperationTool.edit(playlist);
			return SUCCESS;

		} catch (JDOMException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getStackTrace());
			}
			return ERROR;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getStackTrace());
			}
			return ERROR;
		}
	}

	// push playlist -- generate a ZIP file and transfer to remote device
	public String pushPlaylist() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("start to get the details about this playlist...");
			}
			ActionContext context = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) context
					.get(ServletActionContext.HTTP_REQUEST);
			String id = request.getParameter("id");
			String generatedZipFilePath = generatePlaylistZipFile(id);
			if (generatedZipFilePath != null) {
				// call some method to send the file to the remote device
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * @param id
	 *            playlist id
	 * @return the generated zip file path
	 */
	private String generatePlaylistZipFile(String id) {
		String generatedZipFilePath = null;
		try {
			String type = id.split("_")[0];
			if (type.equals(XmlOperationTool.DB_TYPE_PLAYLIST)) {
				PlaylistDTO downloadPlaylistDTO = null;
				List<PlaylistDTO> playlistList = playlistService.getPlaylist();

				// 查询出下载的playlist
				for (PlaylistDTO playlist : playlistList) {
					if (id.equals(playlist.getId())) {
						downloadPlaylistDTO = playlist;
					}
				}
				if (log.isDebugEnabled()) {
					log
							.debug("the palylist you want to transfer to remote device is:"
									+ downloadPlaylistDTO.getId());
				}

				// 查询出playlist中programlist
				List<ProgramDTO> programs1 = downloadPlaylistDTO
						.getProgramList();
				List<ProgramDTO> programs2 = programService.getProgramList();
				List<ProgramDTO> programList = new ArrayList<ProgramDTO>();

				// 得到所有资源文件id和name
				Map<String, String> resourceMap = new HashMap<String, String>();

				// 将带有所有属性的ProgramDTO存到programList中
				for (ProgramDTO program1 : programs1) {
					for (ProgramDTO program2 : programs2) {
						if (program1.getId().equals(program2.getId())) {
							program2.setIndexNum(program1.getIndexNum());
							programList.add(program2);

							// 获取节目的资源name
							for (OnebyoneDTO onebyone : program2
									.getOnebyoneList()) {
								resourceMap.put(onebyone.getResource(),
										onebyone.getResourceName());
							}
						}
					}
				}

				downloadPlaylistDTO.setProgramList(programList);

				// 创建目录
				File dirFileFolder = new File(
						XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER);
				File zipFileFolder = new File(
						FileOperationTool.DEFAULT_ZIP_PATH);
				if (!dirFileFolder.exists()) {
					if (!dirFileFolder.mkdir()) {
						if (log.isErrorEnabled()) {
							log.error(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
									+ "目录创建失败");
						}
					}
				}
				if (!zipFileFolder.exists()) {
					if (!zipFileFolder.mkdir()) {
						if (log.isErrorEnabled()) {
							log.error(FileOperationTool.DEFAULT_ZIP_PATH
									+ "目录创建失败");
						}
					}
				}

				// 创建文件
				String xmlFilePath = XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
						+ id + "_" + System.currentTimeMillis() + ".xml";
				String zipFilePath = FileOperationTool.DEFAULT_ZIP_PATH + id
						+ "_" + System.currentTimeMillis() + ".rar";

				// 显示的文件名
				fileName = id + "_" + System.currentTimeMillis() + ".rar";
				if (log.isDebugEnabled()) {
					log
							.debug("the file name you want to transfer to remote device is :"
									+ fileName);
				}
				File xmlfile = new File(xmlFilePath);
				if (xmlfile.createNewFile()) {
					// 创建palylist xml 文件
					XmlOperationTool.newPlaylistXML(downloadPlaylistDTO,
							xmlFilePath);

					// 创建用来打包的文件
					List<File> resourceList = new ArrayList<File>();
					resourceList.add(xmlfile);
					for (Map.Entry<String, String> entry : resourceMap
							.entrySet()) {
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						if (key.contains("video")) {
							File resourceFile = new File(
									FileOperationTool.DEFAULT_VIDEO_DES_PATH
											+ value);
							resourceList.add(resourceFile);
						}
						if (key.contains("audio")) {
							File resourceFile = new File(
									FileOperationTool.DEFAULT_AUDIO_DES_PATH
											+ value);
							resourceList.add(resourceFile);
						}
						if (key.contains("image")) {
							File resourceFile = new File(
									FileOperationTool.DEFAULT_IMG_DES_PATH
											+ value);
							resourceList.add(resourceFile);
						}
						if (key.contains("text")) {
							File resourceFile = new File(
									FileOperationTool.DEFAULT_TEXT_DES_PATH
											+ value);
							resourceList.add(resourceFile);
						}
					}

					path = zipFilePath;
					generatedZipFilePath = zipFilePath;

					// 生成zip文件
					FileOutputStream fous = new FileOutputStream(new File(
							zipFilePath));
					ZipOutputStream zipOut = new ZipOutputStream(fous);
					zipOut.setEncoding("gbk");
					FileOperationTool.zipFile(resourceList, zipOut);

					zipOut.close();
					generatedZipFilePath = zipFilePath;
				} else {
					if (log.isErrorEnabled()) {
						log.error(XmlOperationTool.PLAYLIST_TEMPFILE_FOLDER
								+ id + ".xml文件创建失败");
					}
				}
			}
		} catch (FileNotFoundException e) {
			if (log.isErrorEnabled()) {
				log.error("Throw a FileNotFoundException !!!");
				log.error(e.getMessage());
			}
		} catch (JDOMException e) {
			if (log.isErrorEnabled()) {
				log.error("Throw a JDOMException !!!");
				log.error(e.getMessage());
			}
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("Throw a IOException !!!");
				log.error(e.getMessage());
			}
		}
		return generatedZipFilePath;
	}

	public List<PlaylistDTO> getPlaylistList() {
		return playlistList;
	}

	public void setPlaylistList(List<PlaylistDTO> playlistList) {
		this.playlistList = playlistList;
	}

	public List<ProgramDTO> getProgramList() {
		return programList;
	}

	public void setProgramList(List<ProgramDTO> programList) {
		this.programList = programList;
	}

	public PlaylistService getPlaylistService() {
		return playlistService;
	}

	public void setPlaylistService(PlaylistService playlistService) {
		this.playlistService = playlistService;
	}

	public ProgramService getProgramService() {
		return programService;
	}

	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	public PlaylistDTO getPlaylistObject() {
		return playlistObject;
	}

	public void setPlaylistObject(PlaylistDTO playlistObject) {
		this.playlistObject = playlistObject;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
