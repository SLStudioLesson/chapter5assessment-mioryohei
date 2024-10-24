package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.taskapp.model.*;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
    
                // CSVの形式チェック
                if (values.length != 4) {
                    continue;
                }
    
                int code = Integer.parseInt(values[0]);
                String name = values[1];
                int status = Integer.parseInt(values[2]);
                int repUserCode = Integer.parseInt(values[3]);
    
                // Userオブジェクトの見つかったユーザーとマッチするか検索
                User repUser = userDataAccess.findByCode(repUserCode);
                if (repUser == null) {
                    continue; // マッチしない場合はスキップ
                }
    
                // マッピング
                Task task = new Task(code, name, status, repUser);
                // タスクを追加
                tasks.add(task);
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
        return tasks;
    }

    /**
     * タスクをCSVに保存します。
     * @param task 保存するタスク
     */
    public void save(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = task.getCode() + "," + task.getName() + "," + task.getStatus() + "," + task.getRepUser().getCode();

            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを1件取得します。
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
    public Task findByCode(int code) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
        
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length != 4) {
                continue;
            }

            int taskCode = Integer.parseInt(values[0]);
            String name = values[1];
            int status = Integer.parseInt(values[2]);
            int repUserCode = Integer.parseInt(values[3]);

            Task task = new Task(taskCode, name, status, repUserCode);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * タスクデータを更新します。
     * @param updateTask 更新するタスク
     */
    public void update(Task updateTask) {
        try () {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを削除します。
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    //     try () {

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
    // private String createLine(Task task) {
    // }
}