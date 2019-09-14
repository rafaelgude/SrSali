CREATE TABLE `instituicao_de_ensino` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL
);

CREATE TABLE `ambiente` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  `capacidade_alunos` INT NOT NULL,
  `tipo_ambiente` INT NOT NULL,
  CONSTRAINT `fk_ambiente_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `curso` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  CONSTRAINT `fk_curso_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `disciplina` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  CONSTRAINT `fk_disciplina_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `ferramenta` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  CONSTRAINT `fk_ferramenta_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `horario` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  `hora_fim` TIME NOT NULL,
  `hora_inicio` TIME NOT NULL,
  `turno` INT NOT NULL,
  CONSTRAINT `fk_horario_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `turma` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `quantidade_alunos` INT NOT NULL,
  `curso_id` INT NOT NULL,
  CONSTRAINT `fk_turma_instituicao` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`)
);

CREATE TABLE `usuario` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `convite` VARCHAR(255) DEFAULT NULL,
  `data_hora_convite` DATETIME DEFAULT NULL,
  `email` VARCHAR(60) NOT NULL UNIQUE,
  `nome` VARCHAR(60) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(13) DEFAULT NULL,
  `instituicao_id` INT NOT NULL,
  CONSTRAINT `fk_usuario_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `usuario_funcao` (
  `usuario_id` INT NOT NULL,
  `funcao_id` INT NOT NULL DEFAULT 0,
  CONSTRAINT `fk_usuario_funcao` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
);

CREATE TABLE `usuario_permissao` (
  `usuario_id` INT NOT NULL,
  `permissao_id` INT NOT NULL,
  CONSTRAINT `fk_usuario_permissao` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
);

CREATE TABLE `ambiente_ferramenta` (
  `ambiente_id` INT NOT NULL,
  `ferramenta_id` INT NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`ambiente_id`, `ferramenta_id`),
  CONSTRAINT `fk_ambiente_ferramenta` FOREIGN KEY (`ferramenta_id`) REFERENCES `ferramenta` (`id`),
  CONSTRAINT `fk_ferramenta_ambiente` FOREIGN KEY (`ambiente_id`) REFERENCES `ambiente` (`id`)
);

CREATE TABLE `curso_disciplina` (
  `curso_id` INT NOT NULL,
  `disciplina_id` INT NOT NULL,
  PRIMARY KEY (`curso_id`, `disciplina_id`),
  CONSTRAINT `fk_disciplina_curso` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`),
  CONSTRAINT `fk_curso_disciplina` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`)
);

CREATE TABLE `disciplina_ferramenta` (
  `disciplina_id` INT NOT NULL,
  `ferramenta_id` INT NOT NULL,
  PRIMARY KEY (`disciplina_id`, `ferramenta_id`),
  CONSTRAINT `fk_disciplina_ferramenta` FOREIGN KEY (`ferramenta_id`) REFERENCES `ferramenta` (`id`),
  CONSTRAINT `fk_ferramenta_disciplina` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`)
);

CREATE TABLE `professor` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ativo` BIT(1) NOT NULL,
  `email` VARCHAR(60) DEFAULT NULL,
  `nome` VARCHAR(60) NOT NULL,
  `instituicao_id` INT NOT NULL,
  `usuario_id` INT,
  CONSTRAINT `fk_professor_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_professor_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `reserva` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `data` DATE NOT NULL,
  `pre_reserva` BIT(1) NOT NULL,
  `turno` INT NOT NULL,
  `ambiente_id` INT NOT NULL,
  `disciplina_id` INT,
  `horario_id` INT NOT NULL,
  `instituicao_id` INT NOT NULL,
  `professor_id` INT,
  CONSTRAINT `fk_reserva_ambiente` FOREIGN KEY (`ambiente_id`) REFERENCES `ambiente` (`id`),
  CONSTRAINT `fk_reserva_horario` FOREIGN KEY (`horario_id`) REFERENCES `horario` (`id`),
  CONSTRAINT `fk_reserva_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`),
  CONSTRAINT `fk_reserva_disciplina` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`),
  CONSTRAINT `fk_reserva_instituicao` FOREIGN KEY (`instituicao_id`) REFERENCES `instituicao_de_ensino` (`id`)
);

CREATE TABLE `reserva_turma` (
  `reserva_id` INT NOT NULL,
  `turma_id` INT NOT NULL,
  PRIMARY KEY (`reserva_id`,`turma_id`),
  CONSTRAINT `fk_turma_reserva` FOREIGN KEY (`reserva_id`) REFERENCES `reserva` (`id`),
  CONSTRAINT `fk_reserva_turma` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`)
);