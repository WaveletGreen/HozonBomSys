package com.connor.hozon.bom;

import com.connor.hozon.bom.resources.dto.request.InsertHzPbomMaintainRecordReqDTO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HozonBomApplicationTest {

	@Autowired
	private HzPbomService hzPbomService;
	@Test
	public void contextLoads() {
	}

	@Test
	public void hzPbomLineMaintainTest(){
		List<InsertHzPbomMaintainRecordReqDTO> list = new ArrayList<>();
		for(int i = 0;i<3;i++){
			InsertHzPbomMaintainRecordReqDTO recordReqDTO = new InsertHzPbomMaintainRecordReqDTO();
			recordReqDTO.setChange("1111");
			recordReqDTO.setTools("工具");
			recordReqDTO.setpBomPuid("0e64c05e-46dd-41ea-a882-8c833d8e6a65");
			recordReqDTO.setWasterProduct("废品");
		}
		int i = hzPbomService.insertPbomLineMaintainRecords(list);
		System.out.println(i);
	}
}
