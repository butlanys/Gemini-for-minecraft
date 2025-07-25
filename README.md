# Gemini-for-Minecraft

**Gemini-for-Minecraft** 是一个创新的Minecraft Forge Mod，它将Google的Gemini大语言模型无缝集成到游戏中。现在，您可以通过简单的自然语言聊天指令，让Gemini成为您在Minecraft世界中的智能助手，无论是执行复杂的指令，还是回答您的问题，它都能轻松应对。

## ✨ 功能特性

*   **自然语言指令**：忘记那些繁琐的指令语法吧！直接告诉Gemini您想做什么，比如“给我一把钻石剑”或“把时间设置为夜晚”。
*   **智能对话**：Gemini不仅能执行指令，还能像一个真正的助手一样与您对话，并根据您的输入语言进行智能回复。
*   **高度可配置**：通过游戏内的聊天指令，您可以轻松设置自己的API密钥、API基础URL和使用的模型名称。
*   **安全可靠**：所有敏感信息（如API密钥）都将安全地保存在您的本地游戏存档中，不会泄露或被打包到Mod文件中。
*   **轻量且兼容**：基于Minecraft Forge 1.20.1开发，确保了良好的兼容性和稳定性。

## 🚀 快速开始

### 安装

1.  确保您已经安装了 [Minecraft Forge for 1.20.1](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)。
2.  下载最新的Mod `.jar` 文件。
3.  将 `.jar` 文件放入您Minecraft游戏目录下的 `mods` 文件夹中。
4.  启动游戏！

### 配置

1.  进入您的Minecraft世界。
2.  打开聊天框，输入以下指令来设置您的Gemini API密钥：
    ```
    /gemini setkey <your_google_ai_studio_api_key>
    ```
3.  （可选）您还可以自定义API的基础URL和模型名称：
    ```
    /gemini setbaseurl <your_api_base_url>
    /gemini setmodel <your_model_name>
    ```
4.  配置完成！现在您可以开始与Gemini互动了。

## 🎮 用法

*   **执行指令**:
    ```
    /gemini give me 64 diamonds
    /gemini summon a creeper at my location
    /gemini set the weather to clear
    ```
*   **获取帮助**:
    ```
    /gemini help
    ```

## 🤝 贡献

欢迎您为这个项目做出贡献！无论是提交Bug报告、提出功能建议，还是直接贡献代码，我们都非常欢迎。

## 📄 开源许可

本项目采用 [MIT License](LICENSE.txt) 开源。