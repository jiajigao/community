package com.demo.community.service;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.dto.QuestionDTO;
import com.demo.community.mapper.QuestionMapper;
import com.demo.community.mapper.UserMapper;
import com.demo.community.model.Question;
import com.demo.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> q = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : q) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> q = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : q) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOList);
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        } else {

            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
