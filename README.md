# 🦅 Ravn-Core

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Pub%2FSub-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![License](https://img.shields.io/badge/License-Proprietary-blue?style=for-the-badge)

> **The industrial-grade backbone for next-generation Minecraft networks.**

**Ravn-Core** is not just a library; it's a comprehensive architectural framework designed to handle high-concurrency data streams across distributed server networks. Built to eliminate main-thread bottlenecks through aggressive asynchronous processing.

### ⚡ Technical Architecture
* **Reactive Database Layer:** Non-blocking SQL implementation utilizing **HikariCP** for connection pooling.
* **Cross-Server Sync:** Real-time data synchronization and messaging via **Redis Pub/Sub** (Jedis).
* **Packet-Level Optimization:** Custom packet injection and handling for NMS-independent operations.
* **Fluent API:** Streamlined builders for ItemStacks, GUIs (Inventory), and NBT manipulation.

---

### 📦 Installation (Maven)
Ravn-Core is hosted on our private nexus. To use it in your project:

```xml
<repository>
    <id>ravn-repo</id>
    <url>[https://repo.ravn.dev/releases](https://repo.ravn.dev/releases)</url>
</repository>

<dependency>
    <groupId>dev.ravn</groupId>
    <artifactId>ravn-core</artifactId>
    <version>1.0.0-STABLE</version>
    <scope>provided</scope>
</dependency>
