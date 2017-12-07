package cn.itcast.bos.service.base.impl;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.ArchiveRepository;
import cn.itcast.bos.dao.base.SubArchiveRepository;
import cn.itcast.bos.domain.base.Archive;
import cn.itcast.bos.domain.base.SubArchive;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.base.ArchiveService;

@Service
@Transactional
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private ArchiveRepository archiveReopsitory;
	@Autowired
	private SubArchiveRepository subArchiveRepository;
	
	@Override
	public void saveArchive(Archive archive) {
		//将当前操作数据的用户信息添加到archive
		Object _user =  ServletActionContext.getContext().getSession().get("user");
		if(_user != null){
			User user = (User)_user;
			archive.setOperatingCompany(user.getStation());
			archive.setOperatingTime(new Date());
			archive.setOperator(user.getNickname());
//			archive.setRemark(user.getRemark());
		}
		archiveReopsitory.save(archive);
	}

	@Override
	public void updateArchive(Archive archive) {
		archiveReopsitory.saveAndFlush(archive);
	}

	public void deleteArchive(Integer id){
		archiveReopsitory.delete(id);
	}

	@Override
	public Page<Archive> findPageData(Pageable pageable) {
		
		return archiveReopsitory.findAll(pageable);
	}

	@Override
	public void saveSubArchive(SubArchive subArchive) {
		subArchiveRepository.save(subArchive);
	}

	@Override
	public void updateSubArchive(SubArchive subArchive) {
		subArchiveRepository.saveAndFlush(subArchive);
	}

	@Override
	public void deleteSubArchive(Integer id) {
		subArchiveRepository.delete(id);
	}

	@Override
	public Page<SubArchive> findSubPageData(Pageable pageable) {
		return subArchiveRepository.findAll(pageable);
	}
	
	
}
