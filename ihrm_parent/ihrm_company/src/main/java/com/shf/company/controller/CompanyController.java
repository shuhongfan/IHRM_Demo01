package com.shf.company.controller;

import com.shf.common.entity.Result;
import com.shf.common.entity.ResultCode;
import com.shf.common.exception.CommonException;
import com.shf.company.service.CompanyService;
import com.shf.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

//    保存企业
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody Company company){
//        业务操作
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

//    根据id更新企业
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,
                         @RequestBody Company company){
//        业务操作
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

//    根据id删除企业
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

//    根据id查询企业
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws CommonException {
//        throw new CommonException(ResultCode.UNAUTHORISE);

        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;
    }

//    查询全部企业列表
    @RequestMapping(value="",method = RequestMethod.GET)
    public Result findAll(){
        List<Company> companyList = companyService.findAll();
//        int i = 10/0;
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(companyList);
        return result;
    }
}
