package com.shf.domain.company.response;

import com.shf.domain.company.Company;
import com.shf.domain.company.Department;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts;

    public DeptListResult(Company company,List depts){
        this.companyId=company.getId();
        this.companyName=company.getName();
        this.companyManage=company.getLegalRepresentative(); // 公司联系人
        this.depts=depts;
    }
}
