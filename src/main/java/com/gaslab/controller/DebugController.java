package com.gaslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/tables")
    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT table_name FROM user_tables WHERE table_name LIKE '%SITU%' OR table_name LIKE '%STATE%'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        } catch (Exception e) {
            tables.add("Error: " + e.getMessage());
        }
        return tables;
    }

    @GetMapping("/situations-direct")
    public List<Map<String, Object>> getSituationsDirect() {
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement schemaPs = conn.prepareStatement("SELECT USER FROM DUAL");
            ResultSet schemaRs = schemaPs.executeQuery();
            if (schemaRs.next()) {
                Map<String, Object> schemaInfo = new HashMap<>();
                schemaInfo.put("current_schema", schemaRs.getString(1));
                result.add(schemaInfo);
            }
            
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM SITUATIONS");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getLong("id"));
                    row.put("name", rs.getString("name"));
                    result.add(row);
                }
            } catch (Exception e2) {
                try {
                    PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM GASLAB.SITUATIONS");
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        Map<String, Object> row = new HashMap<>();
                        row.put("id", rs2.getLong("id"));
                        row.put("name", rs2.getString("name"));
                        result.add(row);
                    }
                } catch (Exception e3) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("error1", e2.getMessage());
                    error.put("error2", e3.getMessage());
                    result.add(error);
                }
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("connection_error", e.getMessage());
            result.add(error);
        }
        return result;
    }
}