package com.company.mariosy.controller;

import com.company.mariosy.mappers.MariosMapper;
import com.company.mariosy.repository.MariosRepository;
import com.company.mariosy.repository.UserRepository;
import com.company.mariosy.service.MariosyService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MariosControllerTest {

    @Mock
    MariosRepository mariosRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    MariosMapper mariosMapper;

    @Spy
    MariosyService mariosyService;

    @InjectMocks
    MariosController mariosController;
}


//    @Test
//    @Transactional
//    public void shouldBeMoreMarioses(){
//        //given
//        ControllerTestHelper.createUsersToGivenId(2, userController);
//
//        Long creatorId = 1L;
//        Long receiverId = 2L;
//
//        int mariosesCount = mariosController.getAllMarioses().size();
//        Set<Long> receiversIds = new HashSet<Long>(Sets.newHashSet(receiverId));
//        MariosDTO mariosDTO = ControllerTestHelper.createMariosDTO(creatorId, receiversIds);
//        //when
//        ResponseEntity<String> responseEntity =  mariosController.createMarios(mariosDTO);
//        int newMariosesCount = mariosController.getAllMarioses().size();
//        //then
//        Assertions.assertEquals(mariosesCount+1, newMariosesCount);
//    }

