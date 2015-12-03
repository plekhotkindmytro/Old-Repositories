HighloadKiller
==============
HighloadKiller — cut-and-dried framework for analysis highload on your web project. Framework includes database generator, big queries count emulator and statistics of queries execution.



Developers
==============
Full Name          | Email                       | Skype             | Github           |
-------------------|-----------------------------|-------------------|------------------|
Viktor Vetsko      | viktor.vetsko@gmail.com     | bigset1           | bigset1          |
Dmytro Plekhotkin  | plekhotkindmytro@gmail.com  | dmytro.plekhotkin | plekhotkindmytro |
Denis Dragomirik   | den@lux-blog.org            | denike-web        | denikeweb        |
Alex Torrison      | cd99@mail.ru                | alex_xandr        | torrison         |
Denis Ivanets      | denself@gmail.com           | big_denself       | denself          |
Dima Tkach         |                             |                   |                  |


Areas of use
==============
- highload study
- project prepearing to highload
- long queries optimizing


Technologies
==============
- PHP+MySQL (at php/)
- Java+MySQL (at java/)
- Python+PostgreSQL (in development)

Get started
==============
1. Clone HighloadKiller from git repository
2. Add files to directory at your test server
3. **test_dump.sql** — demo database, you can use it for framework introduction
4. Edit **config.php**: set your database connection parametres and generation parametres
5. Edit **testing_config.php**: set your testing parametres
6. You can enter your select-query in **machine_gun.php**. You can use **make_select_attack ()** for emulation your real query and **make_select_attack2 ()** for testing your optimized query (maby with denormalized database).
7. Run **index.php** in your browser and use UI for database manipulation.

UI
==============
Tab1 [Generate]:
  - clear database data and set default structure
  - clear database with optimized structure
  - generate plug data

Tab2 [HighLoad]:
  - emulate queries of **make_select_attack ()** and **make_select_attack2 ()**
  - server logs for queries
  - AntiStress Button - if you drop down your server click the button to the Elevator came faster!



Manual in GoogleDocs
==============
https://docs.google.com/document/d/1EQaRDtzHf1Nh_7uAluGsB0zj9mRbt2MCQm1NtGNzC2s/edit



