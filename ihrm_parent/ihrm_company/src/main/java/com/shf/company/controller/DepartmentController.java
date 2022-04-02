package com.shf.company.controller;

import com.shf.common.controller.BaseController;
import com.shf.common.entity.Result;
import com.shf.common.entity.ResultCode;
import com.shf.company.service.CompanyService;
import com.shf.company.service.DepartmentService;
import com.shf.domain.company.Company;
import com.shf.domain.company.Department;
import com.shf.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin  // 解决跨域
@RestController
@RequestMapping(value = "/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 保存
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department){
//        1.设置保存的企业id
        /**
         * 企业id：目前使用固定值1，以后就会解决
         */
        department.setCompanyId(companyId);
//        2.调用service完成保存企业
        departmentService.save(department);
//        3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
//        1.指定企业id
        String companyId = "1";
//        2.完成查询
        Company company = companyService.findById(companyId);
//        3.构造返回结果
        List<Department> list = departmentService.findAll(companyId);
        DeptListResult deptListResult = new DeptListResult(company, list);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

//    根据id查询department
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

//    修改department
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Department department){
//        1.设置修改的部门id
        department.setId(id);
//        2.调用service更新
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    // 根据id删除
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
