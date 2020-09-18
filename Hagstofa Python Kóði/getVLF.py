
# skrá sem sækir gögn um Verga landsframleiðslu af
# vefsíðu Hagstofunnar á csv formi. Færir csv yfir í
# pandas DataFrame og prentar út í console. 

import requests
import json
import pandas as pd
import csv

session = requests.Session()

# Vefslóð og query fengin af 
# https://px.hagstofa.is/pxis/pxweb/is/Efnahagur/Efnahagur__thjodhagsreikningar__landsframl__1_landsframleidsla/THJ01102.px
# Fyrst eru gögn í töflunni valin og síðan býr síðan til query sem má afrita
# (fara í Um töflu og expanda "Nota töfluna í eigin kerfum")
# (Ath að default hjá Hagstofu er px skrá, þarf að breyta aftast í query til að fá annað
#  t.d. json eða csv. Hér er sótt csv skrá)
url = 'https://px.hagstofa.is:443/pxis/api/v1/is/Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px'

query = {
  "query": [
    {
      "code": "Mælikvarði",
      "selection": {
        "filter": "item",
        "values": [
          "0"
        ]
      }
    },
    {
      "code": "Skipting",
      "selection": {
        "filter": "item",
        "values": [
          "14"
        ]
      }
    }
  ],
  "response": {
    "format": "csv"
  }
}

response = session.post(url, json = query)
decoded_content = response.content.decode('ISO-8859-1')
cr = csv.reader(decoded_content.splitlines(), delimiter=',')
VLF_nafn = pd.DataFrame(zip(*list(cr)))
VLF_nafn.columns = ['Fjórðungur', 'VLF']
VLF_nafn = VLF_nafn[2:]
print(VLF_nafn)
#VLF_nafn.to_csv('VLF.csv', encoding='ISO-8859-1', sep = ';', index = False)
