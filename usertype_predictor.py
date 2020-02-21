import sys
import random
from random import randrange
import configparser
import os
import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import json
import csv
from sklearn.externals import joblib



def load_model_predict_usertype(user_data,bat_type,datafolder_model):
    '''
    Load and evaluate the models
    '''
    model_path = '{}\\user{}_model.sav'.format(datafolder_model,str(bat_type))
    knn = joblib.load(model_path) 
    #Train the model
    predictions = knn.predict(user_data)
    return predictions

def retrieve_user_history(u_id, bat_type, datafolder_ge):
    data_ge_path = '{}\\ML_UserType_data_Bat{}.csv'.format(datafolder_ge,str(bat_type))
    bat_df = pd.read_csv(data_ge_path)
    user_history = bat_df[bat_df.u_id==u_id]
#     print('actual type',user_history.u_type.iloc[0])
    user_history = user_history.drop(columns=['u_type','u_id'])
    return user_history

def retrieve_users_bat_type(u_id, datafolder_ge):
    data_ge_path = '{}\\user_list.csv'.format(datafolder_ge)
    user_df = pd.read_csv(data_ge_path)
    bat_type = user_df[user_df['u_id'] ==u_id].Battery_Type.iloc[0]
    bat_type = int(bat_type.strip('bat'))
    return bat_type

def read_config():
    config = configparser.ConfigParser()
    config_fileName = ('config.ini')
    config.read(config_fileName)
    datafolder_model =config['MODEL']['MODEL_FOLDER']	
    datafolder_ge =config['DATASET']['DATA_FOLDER_GE']
    return datafolder_model, datafolder_ge


def main(arg):
    datafolder_model, datafolder_ge = read_config()
    user_id = arg
    bat_type = retrieve_users_bat_type(user_id, datafolder_ge)
    user_data = retrieve_user_history(user_id, bat_type, datafolder_ge)
    user_type = load_model_predict_usertype(user_data, bat_type, datafolder_model)
    if user_type== 1:
        utype='LOW'
    elif user_type == 2:
        utype='MID'
    else:
        utype='HIGH'
    print(utype)
    return utype


if __name__ == "__main__":
    main(sys.argv[1])
#     main(1700)

