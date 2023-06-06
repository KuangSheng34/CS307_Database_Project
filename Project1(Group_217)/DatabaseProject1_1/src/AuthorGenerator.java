import com.alibaba.fastjson.JSON;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AuthorGenerator {
    private static List<Post> loadJSONFile() {
        try {
            String jsonStrings = Files.readString(Path.of("resources//posts.json"));
            return JSON.parseArray(jsonStrings,Post.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static List<Replies> loadJSONFile2() {
        try {
            String jsonStrings = Files.readString(Path.of("resources//replies.json"));
            return JSON.parseArray(jsonStrings,Replies.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws ParseException {
        List<Post> posts = loadJSONFile();
        List<Replies> replies = loadJSONFile2();
        List<String> author_name = new ArrayList<>();
        List<String> author_id = new ArrayList<>();
        List<String> author_registration_time = new ArrayList<>();
        List<String> author_phone = new ArrayList<>();
        for (Post p : posts) {
            author_name.add(p.getAuthor());
            author_id.add(p.getAuthorID());
            author_registration_time.add(p.getAuthorRegistrationTime());
            author_phone.add(p.getAuthorPhone());
        }
        Random random = new Random();
        IDGenerator idGenerator = new IDGenerator();
        for (Post p : posts) {
            int year = Integer.parseInt(p.getPostingTime().substring(0, 4));
            int month = Integer.parseInt(p.getPostingTime().substring(5,7));
            int day = Integer.parseInt(p.getPostingTime().substring(8,10));
            List<List<String>> lists = new ArrayList<>();
            lists.add(p.getAuthorFollowedBy());
            lists.add(p.getAuthorWhoFavoritedThePost());
            lists.add(p.getAuthorWhoLikedThePost());
            lists.add(p.getAuthorWhoSharedThePost());
            for (List<String> l: lists) {
                for (String s : l) {
                    int registration_year = year - random.nextInt(year - 1998);
                    int registration_month, registration_day, registration_hour, registration_minute, registration_second;
                    if (registration_year == year) {
                        registration_month = 1 + random.nextInt(month);
                    } else {
                        registration_month = 1 + random.nextInt(12);
                    }
                    if (registration_month == month && registration_year == year) {
                        registration_day = 1 + random.nextInt(day);
                    } else {
                        switch (registration_month) {
                            case 1, 3, 5, 7, 8, 10, 12 :
                                registration_day = random.nextInt(31) + 1;
                                break;
                            case 4, 6, 9, 11 :
                                registration_day = random.nextInt(30) + 1;
                                break;
                            default:
                                if (registration_year % 4 == 0) {
                                    registration_day = random.nextInt(29) + 1;
                                } else {
                                    registration_day = random.nextInt(28) + 1;
                                }
                        }
                    }
                    if (registration_year == year && registration_month == month && registration_day == day) {
                        registration_year--;
                        if (registration_month == 2 && registration_day == 29) {
                            registration_day = 28;
                        }
                    }
                    registration_hour = random.nextInt(24);
                    registration_minute = random.nextInt(60);
                    registration_second = random.nextInt(60);
                    String registration_time = registration_year + "-";
                    if (registration_month < 10) {
                        registration_time += ("0" + registration_month + "-");
                    } else {
                        registration_time += (registration_month + "-");
                    }
                    if (registration_day < 10) {
                        registration_time += ("0" + registration_day + " ");
                    } else {
                        registration_time += (registration_day + " ");
                    }
                    if (registration_hour < 10) {
                        registration_time += ("0" + registration_hour + ":");
                    } else {
                        registration_time += (registration_hour + ":");
                    }
                    if (registration_minute < 10) {
                        registration_time += ("0" + registration_minute + ":");
                    } else {
                        registration_time += (registration_minute + ":");
                    }
                    if (registration_second < 10) {
                        registration_time += ("0" + registration_second);
                    } else {
                        registration_time += registration_second;
                    }
                    int birth_year = registration_year - 10 - random.nextInt(80);
                    int birth_month = random.nextInt(12) + 1;
                    int birth_day;
                    switch (birth_month) {
                        case 1, 3, 5, 7, 8, 10, 12 :
                            birth_day = random.nextInt(31) + 1;
                            break;
                        case 4, 6, 9, 11 :
                            birth_day = random.nextInt(30) + 1;
                            break;
                        default:
                            if (registration_year % 4 == 0) {
                                birth_day = random.nextInt(29) + 1;
                            } else {
                                birth_day = random.nextInt(28) + 1;
                            }
                    }
                    String birth = birth_year + "";
                    if (birth_month < 10) {
                        birth += ("0" + birth_month);
                    } else {
                        birth += birth_month;
                    }
                    if (birth_day < 10) {
                        birth += ("0" + birth_day);
                    } else {
                        birth += birth_day;
                    }
                    String ID;
                    if (random.nextInt(2) == 0) {
                        ID = idGenerator.getIdNo(birth, false);
                    } else {
                        ID = idGenerator.getIdNo(birth, true);
                    }
                    String phone = "1" + (random.nextInt(70) + 30);
                    int countphone = 0;
                    while (countphone <= 7) {
                        phone += random.nextInt(10);
                        countphone++;
                    }
                    int index = author_name.indexOf(s);
                    if (index >= 203) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date1 = sdf.parse(registration_time);
                            Date date2 = sdf.parse(author_registration_time.get(index));
                            if (date1.compareTo(date2) < 0) {
                                author_registration_time.set(index, registration_time);
                                author_id.set(index, ID);
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (index < 0) {
                        author_name.add(s);
                        author_id.add(ID);
                        author_registration_time.add(registration_time);
                        author_phone.add(phone);
                    }
                }
            }
        }
        for (Replies r : replies) {
            Post p = posts.get(r.getPostID());
            int year = Integer.parseInt(p.getPostingTime().substring(0, 4));
            int month = Integer.parseInt(p.getPostingTime().substring(5,7));
            int day = Integer.parseInt(p.getPostingTime().substring(8,10));
            List<String> lists = new ArrayList<>();
            lists.add(r.getReplyAuthor());
            lists.add(r.getSecondaryReplyAuthor());
            for (String s: lists) {
                int registration_year = year - random.nextInt(year - 1998);
                int registration_month, registration_day, registration_hour, registration_minute, registration_second;
                if (registration_year == year) {
                    registration_month = 1 + random.nextInt(month);
                } else {
                    registration_month = 1 + random.nextInt(12);
                }
                if (registration_month == month && registration_year == year) {
                    registration_day = 1 + random.nextInt(day);
                } else {
                    switch (registration_month) {
                        case 1, 3, 5, 7, 8, 10, 12 :
                            registration_day = random.nextInt(31) + 1;
                            break;
                        case 4, 6, 9, 11 :
                            registration_day = random.nextInt(30) + 1;
                            break;
                        default:
                            if (registration_year % 4 == 0) {
                                registration_day = random.nextInt(29) + 1;
                            } else {
                                registration_day = random.nextInt(28) + 1;
                            }
                    }
                }
                if (registration_year == year && registration_month == month && registration_day == day) {
                    registration_year--;
                    if (registration_month == 2 && registration_day == 29) {
                        registration_day = 28;
                    }
                }
                registration_hour = random.nextInt(24);
                registration_minute = random.nextInt(60);
                registration_second = random.nextInt(60);
                String registration_time = registration_year + "-";
                if (registration_month < 10) {
                    registration_time += ("0" + registration_month + "-");
                } else {
                    registration_time += (registration_month + "-");
                }
                if (registration_day < 10) {
                    registration_time += ("0" + registration_day + " ");
                } else {
                    registration_time += (registration_day + " ");
                }
                if (registration_hour < 10) {
                    registration_time += ("0" + registration_hour + ":");
                } else {
                    registration_time += (registration_hour + ":");
                }
                if (registration_minute < 10) {
                    registration_time += ("0" + registration_minute + ":");
                } else {
                    registration_time += (registration_minute + ":");
                }
                if (registration_second < 10) {
                    registration_time += ("0" + registration_second);
                } else {
                    registration_time += registration_second;
                }
                int birth_year = registration_year - 10 - random.nextInt(80);
                int birth_month = random.nextInt(12) + 1;
                int birth_day;
                switch (birth_month) {
                    case 1, 3, 5, 7, 8, 10, 12 :
                        birth_day = random.nextInt(31) + 1;
                        break;
                    case 4, 6, 9, 11 :
                        birth_day = random.nextInt(30) + 1;
                        break;
                    default:
                        if (registration_year % 4 == 0) {
                            birth_day = random.nextInt(29) + 1;
                        } else {
                            birth_day = random.nextInt(28) + 1;
                        }
                }
                String birth = birth_year + "";
                if (birth_month < 10) {
                    birth += ("0" + birth_month);
                } else {
                    birth += birth_month;
                }
                if (birth_day < 10) {
                    birth += ("0" + birth_day);
                } else {
                    birth += birth_day;
                }
                String ID;
                if (random.nextInt(2) == 0) {
                    ID = idGenerator.getIdNo(birth, false);
                } else {
                    ID = idGenerator.getIdNo(birth, true);
                }
                String phone = "1" + (random.nextInt(70) + 30);
                int countphone = 0;
                while (countphone <= 7) {
                    phone += random.nextInt(10);
                    countphone++;
                }
                int index = author_name.indexOf(s);
                if (index >= 203) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date1 = sdf.parse(registration_time);
                        Date date2 = sdf.parse(author_registration_time.get(index));
                        if (date1.compareTo(date2) < 0) {
                            author_registration_time.set(index, registration_time);
                            author_id.set(index, ID);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else if (index < 0) {
                    author_name.add(s);
                    author_id.add(ID);
                    author_registration_time.add(registration_time);
                    author_phone.add(phone);
                }
            }
        }

        File file = new File("resources//authors.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < author_id.size(); i++) {
                bw.write(author_name.get(i));
                bw.write(";");
                bw.write(author_id.get(i));
                bw.write(";");
                bw.write(author_registration_time.get(i));
                bw.write(";");
                bw.write(author_phone.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(author_id.size());
    }
}
