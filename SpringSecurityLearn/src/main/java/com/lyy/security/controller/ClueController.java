package com.lyy.security.controller;

import com.lyy.security.utils.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YueYang
 * Created on 2025/9/22 11:34
 * @version 1.0
 */
@RestController
@RequestMapping("clue")
public class ClueController {

    @PreAuthorize("hasRole('saler')")
    @GetMapping("/clueMenu")
    public R clueMenu() {
        return R.success("clueMenu");
    }

    @PreAuthorize("hasRole('manager')")
    @GetMapping("/clueManage")
    public R clueManage() {
        return R.success("clueManage");
    }

    @PreAuthorize("hasAnyRole('manager','saler')")
    @GetMapping("/clueList")
    public R clueList() {
        return R.success("clueList");
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/clueAdmin")
    public R clueAdmin() {
        return R.success("clueAdmin");
    }


}
