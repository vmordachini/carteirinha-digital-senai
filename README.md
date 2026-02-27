# 🪪 Carteirinha Digital SENAI

Este é um projeto de aprendizado mobile desenvolvido em **Kotlin** com **Jetpack Compose**. O aplicativo funciona como uma carteirinha de estudante digital para alunos do SENAI-SP, apresentando informações do aluno e um QR Code gerado dinamicamente para identificação.

## 🚀 Funcionalidades

- **Identificação Visual:** Exibição da logo oficial do SENAI-SP e foto do aluno.
- **Informações do Aluno:** Nome completo e curso atual.
- **QR Code Dinâmico:** Geração automática de QR Code baseado no número de matrícula/identificação utilizando a biblioteca ZXing.
- **UI Moderna:** Desenvolvido integralmente com Jetpack Compose e Material Design 3.

## 🛠️ Tecnologias e Dependências

O projeto utiliza as seguintes tecnologias:
- **Linguagem:** [Kotlin](https://kotlinlang.org/) (v2.0.21)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/compose) com Material 3
- **Geração de QR Code:** [ZXing (Zebra Crossing)](https://github.com/zxing/zxing)
- **Gerenciador de Dependências:** Gradle (Kotlin DSL) com Version Catalogs (`libs.versions.toml`)

## 📥 Instalação e Execução

Para rodar este projeto localmente, siga os passos abaixo:

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/carteirinha-digital-senai.git](https://github.com/seu-usuario/carteirinha-digital-senai.git)

2. **Abra no Android Studio:**
  Certifique-se de estar utilizando a versão Ladybug (ou superior) para compatibilidade com o Gradle 9.1.

3. **Sincronize o Gradle:**
  O projeto utiliza o JDK 21 para compilação.

4. **Execute o App:**
  Conecte um dispositivo físico ou utilize um emulador com API 24 (Android 7.0) ou superior.

## 📁 Estrutura do Projeto

**Abaixo estão os arquivos principais que compõem a lógica do app:**

  - MainActivity.kt: Contém o componente CarteirinhaDeEstudante que organiza a interface principal.

  - QrCode.kt: Lógica de geração do Bitmap do QR Code através da QRCodeWriter.

  - ui/theme/: Definições de cores, tipografia e tema seguindo o Material Design 3.

  - res/drawable/: Contém os recursos visuais como a logo do SENAI e placeholders de avatar.

