CREATE TABLE tasks (
                       id              UUID            PRIMARY KEY,
                       name            VARCHAR(255)    NOT NULL,
                       is_urgent       BOOLEAN         NOT NULL DEFAULT FALSE,
                       is_important    BOOLEAN         NOT NULL DEFAULT FALSE,
                       due_date        TIMESTAMP,
                       reminder_enabled BOOLEAN        NOT NULL DEFAULT FALSE,
                       is_completed    BOOLEAN         NOT NULL DEFAULT FALSE,
                       created_at      TIMESTAMP       NOT NULL,
                       updated_at      TIMESTAMP       NOT NULL
);

CREATE TABLE subtasks (
                          id               UUID           PRIMARY KEY,
                          task_id          UUID           NOT NULL,
                          name             VARCHAR(255)   NOT NULL,
                          due_date         TIMESTAMP,
                          reminder_enabled BOOLEAN        NOT NULL DEFAULT FALSE,
                          is_completed     BOOLEAN        NOT NULL DEFAULT FALSE,
                          created_at       TIMESTAMP      NOT NULL,
                          CONSTRAINT fk_subtask_task FOREIGN KEY (task_id)
                              REFERENCES tasks(id) ON DELETE CASCADE
);