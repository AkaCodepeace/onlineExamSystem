package cn.com.testol.dao;

import cn.com.testol.DTO.*;
import cn.com.testol.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamDao {
    int deleteByPrimaryKey(Integer examId);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer examId);

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);

    List<ExamUserDTO> selectByCreatorId(Integer userId, String keyword);

    List<ExamClassesDTO> selectByClassesId(Integer classesId,Integer userId);

    ExamTopicStuDTO stuSelectByPrimaryKey(Integer examId,Integer classesId,Integer userId);

    ExamTopicTchDTO tchSelectByPrimaryKey(Integer examId);

    MarkExamInfoDTO selestStuExamInfo(Integer classesId, Integer examId, Integer userId);


    List<ExamUserDTO> selectAllExam(String keyword);


//    Exam selectByExamName(String examName);
}