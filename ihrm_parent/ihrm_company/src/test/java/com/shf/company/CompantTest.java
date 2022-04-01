package com.shf.company;

import com.shf.domain.company.Company;
import com.shf.company.dao.CompanyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CompantTest {
    @Autowired
    private CompanyDao companyDao;

    @Test
    public void testFindById(){
        Company company = companyDao.findById("1").get();
        System.out.println(company);
    }

    @Test
    public void testFindAll(){
        List<Company> companyList = companyDao.findAll();
        System.out.println(companyList);
    }
}
