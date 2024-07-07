from faker import Faker
import random

def generate_data(num_records, filename='operations.txt'):
    faker = Faker()  # 创建 Faker 生成器
    zones = ['zone1', 'zone2', 'zone3']
    pass_types = ['daypass', 'weekpass', 'monthpass']
    
    with open(filename, 'w') as file:
        for _ in range(num_records):
            name = faker.name()  # 使用 Faker 生成随机姓名
            zone = random.choice(zones)  # 随机选择区域
            pass_type = random.choice(pass_types)  # 随机选择通行证类型
            file.write(f"{name}, {zone}, {pass_type}\n")  # 写入文件

if __name__ == '__main__':
    generate_data(1000000)  # 生成 10000 条记录
