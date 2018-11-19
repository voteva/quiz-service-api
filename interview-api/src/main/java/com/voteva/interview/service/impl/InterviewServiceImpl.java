package com.voteva.interview.service.impl;

import com.voteva.interview.exception.NotFoundQuestionException;
import com.voteva.interview.model.entity.CategoryEntity;
import com.voteva.interview.model.entity.QuestionEntity;
import com.voteva.interview.repository.CategoryRepository;
import com.voteva.interview.repository.QuestionsRepository;
import com.voteva.interview.service.InterviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    private static final Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

    private final QuestionsRepository questionsRepository;
    private final CategoryRepository categoryRepository;

    public InterviewServiceImpl(
            QuestionsRepository questionsRepository,
            CategoryRepository categoryRepository) {
        this.questionsRepository = questionsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<QuestionEntity> getAllQuestions(Pageable pageable) {
        return questionsRepository.findAll(pageable);
    }

    @Override
    public Page<QuestionEntity> getQuestionsByCategory(String category, Pageable pageable) {
        return questionsRepository.findByCategory(category, pageable);
    }

    @Override
    public List<String> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionEntity getQuestion(UUID questionUid) {
        return questionsRepository.findByQuestionUid(questionUid)
                .orElseThrow(() -> {
                    logger.debug("Not found question with uid={}", questionUid);

                    return new NotFoundQuestionException("Not found test with uid=" + questionUid);
                });
    }

    @Override
    @Transactional
    public UUID addQuestion(QuestionEntity entity) {
        if (!categoryRepository.findByName(entity.getCategory()).isPresent()) {
            logger.debug("Add new category with name={}", entity.getCategory());

            categoryRepository.save(new CategoryEntity().setName(entity.getCategory()));
        }

        return questionsRepository.save(entity).getQuestionUid();
    }

    @Override
    public void removeQuestion(UUID questionUid) {
        questionsRepository.deleteByQuestionUid(questionUid);
    }
}
